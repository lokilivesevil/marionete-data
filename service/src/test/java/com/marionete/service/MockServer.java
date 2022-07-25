package com.marionete.service;

import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;

public class MockServer {
    static {
        UserInfoMock.start();
        AccountInfoMock.start();
    }
}
