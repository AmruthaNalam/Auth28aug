package com.auth._workfoxtech.services.ServiceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") // pulls from application.properties
    private String fromEmail;

    public  void sendOtp(String email,String otp){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("");
        simpleMailMessage.setSubject("Your OTP Code");
        simpleMailMessage.setText("Your OTP is: " + otp + "\nIt expires in 5 minutes.");
        javaMailSender.send(simpleMailMessage);
    }

}
