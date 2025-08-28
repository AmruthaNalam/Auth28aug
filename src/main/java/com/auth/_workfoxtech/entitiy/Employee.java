package com.auth._workfoxtech.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    private Long id;
    private String name;
    private  String username;
    private  String email;
    private  String password;

}
