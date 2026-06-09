package com.example.employeemanagement.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.employeemanagement.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>
{
	
    Payment findByRazorpayOrderId(String razorpayOrderId);

}