package com.marionete.service;

import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.service.exception.AccountServiceException;
import com.marionete.service.grpc.LoginServiceServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;

@SpringBootApplication
@EnableAsync
@EnableFeignClients
@EnableRetry
public class AccountServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceApplication.class);

    @Value("${grpc.server_port}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    /**
     * Using the springBoot event listener to initialize the GRPC server
     * and also run the mocks for running in the dev environment.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void startGRPCServerAfterStartup() {
        try {
            LoginServiceServer.main(new String[]{port});
            /*  Uncomment when running this in dev environment

                UserInfoMock.start();
                AccountInfoMock.start();
            */
        } catch (IOException e) {
            log.error("Error starting the application. Exception is {}", e.getMessage());
            throw new AccountServiceException();
        }
    }

}

