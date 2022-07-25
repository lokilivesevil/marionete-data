package com.marionete.service.dto.useraccount;

import com.marionete.service.dto.account.AccountDetailsDto;
import com.marionete.service.dto.user.UserDetailsDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserAccountInfoResponseDto {

    private AccountDetailsDto accountInfo;
    private UserDetailsDto userInfo;
}