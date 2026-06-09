package com.example.employeemanagement.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeLoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    // Getter & Setter
}