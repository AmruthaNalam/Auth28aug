package com.auth._workfoxtech.entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OtpToken")
public class OtpToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String otp;

    private LocalDateTime expiryTime;
    public OtpToken(String email,String otp,LocalDateTime expiryTime){
        this.email=email;
        this.otp=otp;
        this.expiryTime=expiryTime;
    }
}
