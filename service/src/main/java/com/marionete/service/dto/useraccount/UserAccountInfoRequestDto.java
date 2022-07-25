package com.marionete.service.dto.useraccount;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Data
public class UserAccountInfoRequestDto {
    @NotEmpty
    @Size(min = 2, message = "Username should have atleast 2 characters")
    private String username;
    @NotEmpty
    @Size(min = 8, message = "Username should have atleast 2 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must satisfy minimum eight characters, at least one uppercase letter," +
                    " one lowercase letter, one number and one special character")
    private String password;
}
