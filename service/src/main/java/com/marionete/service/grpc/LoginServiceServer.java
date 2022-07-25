package com.marionete.service.grpc;

import com.marionete.proto.LoginService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

public class LoginServiceServer {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceServer.class);

    private final Server server;

    public LoginServiceServer(Server server) {
        this.server = server;
    }

    /**
     * Checks if the gRPC server is already running
     * @param portNr
     * @return
     */
    public static boolean isAvailable(int portNr) {
        boolean portFree;
        try (var ignored = new ServerSocket(portNr)) {
            portFree = true;
        } catch (IOException e) {
            portFree = false;
        }
        return portFree;
    }

    /**
     * The main method for the gRPC server. Invoked after the application starts.
     * Creates a server and registers the login service to it.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (isAvailable(Integer.parseInt(args[0]))) {
            LoginServiceServer loginServiceServer = new LoginServiceServer(ServerBuilder.forPort(Integer.parseInt(args[0])).addService(new LoginService()).build());
            loginServiceServer.start();
        }
    }

    /**
     * Start serving requests.
     */
    public void start() throws IOException {
        server.start();
        log.info("Server started, listening on {}", server.getPort());
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                log.info("*** shutting down gRPC server since JVM is shutting down");
                try {
                    LoginServiceServer.this.stop();
                } catch (InterruptedException e) {
                    log.error("Exception while starting the server. Exception is {}", e.getMessage());
                    Thread.currentThread().interrupt();
                }
                log.error("*** server shut down");
            }
        });
    }

    /**
     * Stop serving requests and shutdown resources.
     */
    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

}