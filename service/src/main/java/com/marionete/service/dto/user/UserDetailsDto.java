package com.marionete.service.dto.user;

import com.marionete.service.enums.Sex;
import lombok.Data;

@Data
public class UserDetailsDto {

    private String name;
    private String surname;
    private Sex sex;
    private Integer age;

}
