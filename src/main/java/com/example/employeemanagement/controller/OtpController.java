package com.example.employeemanagement.controller;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.OtpService;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public String sendOtp(@RequestParam String mobile) {

        otpService.sendOtp(mobile);

        return "OTP Sent";
    }
    
    
    
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    
    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String mobile,
                            @RequestParam String otp) {

//        if (mobile.length() == 10) {
//            mobile = "+91" + mobile;
//        }

        Employee emp = employeeRepository.findByPhoneNo(mobile)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (otp.equals(emp.getOtp())) 
        {
            emp.setOtp(null); // clear after success
            employeeRepository.save(emp);
            return "OTP verified successfully";
        }

        return "Invalid OTP";
    }
}
*/