package com.marionete.service.service;

import com.marionete.service.dto.useraccount.UserAccountInfoRequestDto;
import com.marionete.service.dto.useraccount.UserAccountInfoResponseDto;

public interface AccountService {

    UserAccountInfoResponseDto fetchUserAndAccountInfo(UserAccountInfoRequestDto userAccountInfoRequestDto);
}
