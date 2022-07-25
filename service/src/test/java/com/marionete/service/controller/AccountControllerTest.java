package com.marionete.service.controller;

import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.service.MockServer;
import com.marionete.service.dto.useraccount.UserAccountInfoRequestDto;
import com.marionete.service.dto.useraccount.UserAccountInfoResponseDto;
import com.marionete.service.enums.Sex;
import com.marionete.service.exception.LoginException;
import com.marionete.service.grpc.LoginServiceClient;
import com.marionete.service.service.AccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class AccountControllerTest extends MockServer {

    @Autowired
    AccountController accountController;

    @MockBean
    LoginServiceClient loginServiceClient;

    @Test
    void fetchUserDetails_SUCCESS() {
        when(loginServiceClient.login(anyString(), anyString())).thenReturn("123123-4353589sdf-234324-21323");
        UserAccountInfoRequestDto requestDto = UserAccountInfoRequestDto.builder()
                .username("lokesh")
                .password("A123*@nasdaskds")
                .build();

        UserAccountInfoResponseDto response = accountController.fetchUserAndAccountInfo(requestDto);
        assertEquals("12345-3346-3335-4456", response.getAccountInfo().getAccountNumber());
        assertEquals( "John", response.getUserInfo().getName());
        assertEquals("Doe", response.getUserInfo().getSurname());
        assertEquals(Sex.male, response.getUserInfo().getSex());
        assertEquals(32, response.getUserInfo().getAge());
    }

}