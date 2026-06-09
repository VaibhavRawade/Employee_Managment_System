package com.example.employeemanagement.service;
/*
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class WhatsAppService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendOtp(String phoneNumber) {

        if (phoneNumber.length() == 10) {
            phoneNumber = "+91" + phoneNumber;
        }

        String otp = String.valueOf(
                (int)(Math.random() * 900000) + 100000
        );

        Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + phoneNumber),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                "Your OTP is: " + otp
        ).create();
    }
}
*/