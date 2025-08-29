package com.auth._workfoxtech.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SigninResponse {
    private int statusCode;
    private String role;
    private String message;
    private List<Object> data;
}
