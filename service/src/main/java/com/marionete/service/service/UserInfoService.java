package com.marionete.service.service;

import com.marionete.service.dto.user.UserDetailsDto;

public interface UserInfoService {

    UserDetailsDto fetchUserDetails(String authToken);

}
