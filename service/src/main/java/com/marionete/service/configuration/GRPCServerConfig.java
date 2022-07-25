package com.marionete.service.configuration;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GRPCServerConfig {

    @Value("${grpc.server_port}")
    private int port;

    @Bean
    public Server defaultGRPCServer() {
        return ServerBuilder.forPort(port).build();
    }
}