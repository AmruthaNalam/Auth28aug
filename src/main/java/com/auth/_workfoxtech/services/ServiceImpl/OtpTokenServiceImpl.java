package com.auth._workfoxtech.services.ServiceImpl;

import com.auth._workfoxtech.common.ConstantMessage;
import com.auth._workfoxtech.dto.OtpValidDto;
import com.auth._workfoxtech.entitiy.OtpToken;
import com.auth._workfoxtech.repository.EmployeeRepository;
import com.auth._workfoxtech.repository.OtpTokenRepository;
import com.auth._workfoxtech.services.OtpTokenService;
import com.auth._workfoxtech.util.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpTokenServiceImpl implements OtpTokenService {

    private final OtpTokenRepository otpTokenRepository;

    private final EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<ResponseMessage> generateOtp(String email,EmailServiceImpl emailService) {
        if(employeeRepository.findByEmail(email).isEmpty()){
          return new ResponseEntity<>(new ResponseMessage(ConstantMessage.AE_USER_NOT_FOUND,List.of(),ConstantMessage.EmailId_NotExisted),HttpStatus.NOT_FOUND);
        }
        String otp=String.valueOf(new Random().nextInt(900000)+100000);
        LocalDateTime expiryTime=LocalDateTime.now().plusMinutes(5);
        emailService.sendOtp(email,otp);
        otpTokenRepository.deleteByEmail(email);
        otpTokenRepository.save(new OtpToken(email,otp,expiryTime));
        return new ResponseEntity<>(new ResponseMessage(ConstantMessage.AE_LOGIN_SUCCESS, List.of(),ConstantMessage.OTP_GENERATED), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ResponseMessage> validateOtp(OtpValidDto otpValidDto) {
        return otpTokenRepository.findByEmail(otpValidDto.getEmail())
                .filter(otpToken->otpToken.getOtp().equals(otpValidDto.getOtp()))
                .filter(otpToken -> otpToken.getExpiryTime().isAfter(LocalDateTime.now()))
                .isPresent()? new ResponseEntity<>(new ResponseMessage(ConstantMessage.AE_LOGIN_SUCCESS,List.of(),ConstantMessage.OTP_VALIDATED),HttpStatus.ACCEPTED)
                :new ResponseEntity<>(new ResponseMessage(ConstantMessage.AE_UNAUTHORIZED,List.of(),ConstantMessage.OTP_INVALID),HttpStatus.UNAUTHORIZED);
    }

    private void deleteOtp(String email){
        otpTokenRepository.deleteByEmail(email);
    }
}
