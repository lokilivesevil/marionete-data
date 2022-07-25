package com.marionete.service.service.impl;

import com.marionete.service.client.AccountServiceClient;
import com.marionete.service.dto.account.AccountDetailsDto;
import com.marionete.service.exception.AccountServiceException;
import com.marionete.service.service.AccountInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    private static final Logger log = LoggerFactory.getLogger(AccountInfoServiceImpl.class);

    @Autowired
    AccountServiceClient accountServiceClient;

    @Override
    @Retryable(value = Exception.class, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public AccountDetailsDto fetchAccountDetails(String authToken) {
        log.info("Fetching the account details for the user");
        try {
            return accountServiceClient.fetchAccountDetails(authToken);
        } catch (RestClientException e) {
            log.error("Error fetching the account details for the user. Exception is: {}", e.getMessage());
            throw new AccountServiceException();
        } catch (Exception e) {
            log.error("Unexpected error while fetching the account details for the customer. Exception is {}", e.getMessage());
            throw new AccountServiceException();
        }
    }
}