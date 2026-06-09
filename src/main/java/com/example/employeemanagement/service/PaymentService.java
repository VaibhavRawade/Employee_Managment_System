package com.example.employeemanagement.service;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.employeemanagement.dto.VerifyPaymentRequest;
import com.example.employeemanagement.model.Payment;
import com.example.employeemanagement.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
//
//@Service
//public class PaymentService {
//
//    @Value("${razorpay.key.id}")
//    private String keyId;
//
//    @Value("${razorpay.key.secret}")
//    private String keySecret;
//    
//    @Autowired
//    private PaymentRepository paymentRepository;
// 
//    public String createOrder(Integer amount) throws Exception 
//    {
//
//        RazorpayClient client =
//                new RazorpayClient(keyId, keySecret);
//
//        JSONObject orderRequest = new JSONObject();
//
//        Integer actualAmount = amount;
//
//        orderRequest.put("amount", actualAmount * 100);
//        orderRequest.put("currency", "INR");
//        orderRequest.put(
//                "receipt",
//                "EMP_" + System.currentTimeMillis()
//        );
//
//        Order order = client.orders.create(orderRequest);
//
//        Payment payment = new Payment();
//
//        payment.setUserId(1L);
//        payment.setAmount(actualAmount.doubleValue());
//        payment.setRazorpayOrderId(order.get("id").toString());
//        payment.setStatus("PENDING");
//         payment.setCreatedDate(LocalDateTime.now());
//
//        paymentRepository.save(payment);
//
//        return order.toString();
//    }
//    
//    
//    
//    public String verifyPayment(VerifyPaymentRequest request) 
//    {
//
//        Payment payment =
//                paymentRepository.findByRazorpayOrderId(
//                        request.getRazorpayOrderId());
//
//        if (payment == null) {
//            return "Payment record not found";
//        }
//
//        payment.setRazorpayPaymentId(
//                request.getRazorpayPaymentId());
//
//        payment.setStatus("SUCCESS");
//
//        paymentRepository.save(payment);
//
//        return "Payment Verified Successfully";
//    }
//    
//    
//}