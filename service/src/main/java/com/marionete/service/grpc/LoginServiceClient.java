package com.marionete.service.grpc;

import com.marionete.service.exception.LoginException;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;
import services.LoginServiceGrpc.LoginServiceBlockingStub;

@Component
public class LoginServiceClient {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceClient.class);

    private final LoginServiceBlockingStub blockingStub;

    public LoginServiceClient(Channel channel) {
        blockingStub = LoginServiceGrpc.newBlockingStub(channel);
    }

    /**
     * The login method that abstracts the gRPC client.
     * Uses the blockingStub to make gRPC calls.
     * @param username
     * @param password
     * @return
     * @throws LoginException
     */
    public String login(String username, String password) throws LoginException {
        LoginRequest loginRequest = LoginRequest.newBuilder().setUsername(username).setPassword(password).build();
        LoginResponse loginResponse;
        try {
            loginResponse = blockingStub.login(loginRequest);
            return loginResponse.getToken();
        } catch (StatusRuntimeException e) {
            log.error("Login RPC failed: {}: {}", e.getStatus(), e.getMessage());
            throw new LoginException();
        } catch (Exception e) {
            log.error("Unexpected error while login: {}, {}", e.getCause(), e.getMessage());
            throw new LoginException();
        }
    }
}
