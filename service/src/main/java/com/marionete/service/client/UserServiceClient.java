package com.marionete.service.client;

import com.marionete.service.dto.user.UserDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "userServiceClient", url = "${microservices.userInfoService.url}")
@Component
public interface UserServiceClient {

    @GetMapping(path = "${microservices.userInfoService.endpoint}", consumes = "application/json")
    UserDetailsDto fetchUserDetails(@RequestHeader("Authorization") String token);
}
