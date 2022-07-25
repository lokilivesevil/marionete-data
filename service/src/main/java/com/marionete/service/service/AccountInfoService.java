package com.marionete.service.service;

import com.marionete.service.dto.account.AccountDetailsDto;

public interface AccountInfoService {

    AccountDetailsDto fetchAccountDetails(String authToken);

}
