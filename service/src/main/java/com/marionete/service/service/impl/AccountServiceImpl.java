package com.marionete.service.service.impl;

import com.marionete.service.dto.account.AccountDetailsDto;
import com.marionete.service.dto.user.UserDetailsDto;
import com.marionete.service.dto.useraccount.UserAccountInfoRequestDto;
import com.marionete.service.dto.useraccount.UserAccountInfoResponseDto;
import com.marionete.service.grpc.LoginServiceClient;
import com.marionete.service.service.AccountInfoService;
import com.marionete.service.service.AccountService;
import com.marionete.service.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    LoginServiceClient loginServiceClient;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    AccountInfoService accountInfoService;

    @Autowired
    @Qualifier(value = "defaultTaskExecuter")
    Executor asyncExecutor;

    @Override
    public UserAccountInfoResponseDto fetchUserAndAccountInfo(UserAccountInfoRequestDto userAccountInfoRequestDto) {
        log.info("Initiating login request to fetch token for the user {}", userAccountInfoRequestDto.getUsername());
        final String token = loginServiceClient.login(userAccountInfoRequestDto.getUsername(), userAccountInfoRequestDto.getPassword());
        log.info("Login successful for the user {}", userAccountInfoRequestDto.getUsername());

        CompletableFuture<UserDetailsDto> userDetailsTask = CompletableFuture.supplyAsync(() -> userInfoService.fetchUserDetails(token), asyncExecutor);
        CompletableFuture<AccountDetailsDto> accountDetailsTask = CompletableFuture.supplyAsync(() -> accountInfoService.fetchAccountDetails(token), asyncExecutor);

        CompletableFuture.allOf(userDetailsTask, accountDetailsTask);
        return UserAccountInfoResponseDto.builder().userInfo(userDetailsTask.join()).accountInfo(accountDetailsTask.join()).build();
    }
}
