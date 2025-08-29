package com.auth._workfoxtech.repository;

import com.auth._workfoxtech.entitiy.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmail(String username);
}
