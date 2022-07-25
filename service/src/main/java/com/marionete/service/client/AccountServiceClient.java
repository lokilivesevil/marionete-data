package com.marionete.service.client;

import com.marionete.service.dto.account.AccountDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "accountServiceClient", url = "${microservices.accountInfoService.url}")
@Component
public interface AccountServiceClient {

    @GetMapping(path = "${microservices.accountInfoService.endpoint}", consumes = "application/json")
    AccountDetailsDto fetchAccountDetails(@RequestHeader("Authorization") String authToken);
}
