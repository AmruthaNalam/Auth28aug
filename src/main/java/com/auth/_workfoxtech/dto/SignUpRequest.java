package com.auth._workfoxtech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "first name should not be null")
    private String name;

    @Email
    private String email;
    @NotBlank(message = "password should not be blank")
    private String password;

}