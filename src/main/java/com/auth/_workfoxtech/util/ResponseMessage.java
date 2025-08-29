package com.auth._workfoxtech.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {
    private int statusCode;
    private List<Object> data;
    private String message;
}
