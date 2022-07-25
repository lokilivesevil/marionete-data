package com.marionete.proto;

import io.grpc.stub.StreamObserver;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;

import java.util.UUID;

public class LoginService extends LoginServiceGrpc.LoginServiceImplBase {

    @Override
    public void login(LoginRequest loginRequest, StreamObserver<LoginResponse> responseObserver) {
        LoginResponse loginResponse = LoginResponse.newBuilder().setToken(UUID.randomUUID().toString()).build();
        responseObserver.onNext(loginResponse);
        responseObserver.onCompleted();
    }
}