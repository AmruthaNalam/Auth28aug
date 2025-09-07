package com.auth._workfoxtech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordAfterValidOtpRequest {

    private String newPassword;
    private String conformPassword;
}
