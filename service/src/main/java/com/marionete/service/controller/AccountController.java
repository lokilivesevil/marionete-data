package com.marionete.service.controller;

import com.marionete.service.dto.useraccount.UserAccountInfoRequestDto;
import com.marionete.service.dto.useraccount.UserAccountInfoResponseDto;
import com.marionete.service.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/marionete/useraccount/")
    public UserAccountInfoResponseDto fetchUserAndAccountInfo(@Valid @RequestBody UserAccountInfoRequestDto userAccountInfoRequestDto) {
        log.info("Received request to fetch details. Request: {}", userAccountInfoRequestDto);
        return accountService.fetchUserAndAccountInfo(userAccountInfoRequestDto);
    }

}
