package com.auth._workfoxtech.services;

import com.auth._workfoxtech.dto.OtpValidDto;
import com.auth._workfoxtech.services.ServiceImpl.EmailServiceImpl;
import com.auth._workfoxtech.util.ResponseMessage;
import org.springframework.http.ResponseEntity;

public interface OtpTokenService {

    public ResponseEntity<ResponseMessage> generateOtp(String email, EmailServiceImpl emailService);

    public ResponseEntity<ResponseMessage> validateOtp(OtpValidDto otpValidDto);
}
