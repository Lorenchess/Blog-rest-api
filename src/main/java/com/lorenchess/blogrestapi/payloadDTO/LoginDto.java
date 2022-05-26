package com.lorenchess.blogrestapi.payloadDTO;

import lombok.Data;

@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
