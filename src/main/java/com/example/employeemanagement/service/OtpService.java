package com.example.employeemanagement.service;
/*
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.twilio.Twilio;

@Service
public class OtpService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendOtp(String phoneNumber) {

        

        // 1. find employee
        Employee emp = employeeRepository.findByPhoneNo(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

     // normalize
        if (phoneNumber != null && phoneNumber.length() == 10) {
            phoneNumber = "+91" + phoneNumber;
        }
        
        // 2. generate OTP
        String otp = generateOtp();

        // 3. save OTP in DB
        emp.setOtp(otp);
        employeeRepository.save(emp);

        // 4. send SMS via Twilio
        com.twilio.rest.api.v2010.account.Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                "Your OTP is: " + otp
        ).create();
    }

    private String generateOtp() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
}

*/