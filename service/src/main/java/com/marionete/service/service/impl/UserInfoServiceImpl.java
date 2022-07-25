package com.marionete.service.service.impl;

import com.marionete.service.client.UserServiceClient;
import com.marionete.service.dto.user.UserDetailsDto;
import com.marionete.service.exception.AccountServiceException;
import com.marionete.service.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    UserServiceClient userServiceClient;

    @Override
    @Retryable(value = Exception.class, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public UserDetailsDto fetchUserDetails(String authToken) {
        log.info("Fetching user info");
        try {
            return userServiceClient.fetchUserDetails(authToken);
        } catch (RestClientException e) {
            log.error("Error fetching the user details. Exception is: {}", e.getMessage());
            throw new AccountServiceException();
        } catch (Exception e) {
            log.error("Unexpected error while fetching the user details for the customer. Exception is {}", e.getMessage());
            throw new AccountServiceException();
        }
    }
}
