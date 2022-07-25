package com.marionete.service.configuration;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GRPCChannelConfig {

    private static final Logger log = LoggerFactory.getLogger(GRPCChannelConfig.class);

    @Value("${grpc.server_host}")
    private String host;

    @Value("${grpc.server_port}")
    private int port;

    /**
     * The configuration file that returns the Channel for the gRPC client
     * @return: Channel
     */
    @Bean
    public Channel defaultGRPCChannel() {
        log.info("Channel created for the grpc client on port {}", port);
        return ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
    }

}