package com.bugbusters.swe_backend.service;

import com.bugbusters.swe_backend.dto.PaymentDTO;
import com.bugbusters.swe_backend.entity.Payment;
import com.bugbusters.swe_backend.exception.ResourceNotFoundException;
import com.bugbusters.swe_backend.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
    
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    public Optional<Payment> getPaymentById(Long id){
        return paymentRepository.findById(id);
    }
    
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment createPayment(PaymentDTO paymentDTO){
        Payment payment = new Payment();
        payment.setCardNumber(paymentDTO.getCardNumber());
        payment.setCvv(paymentDTO.getCvv());
        payment.setExpDate(paymentDTO.getExpDate());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setFirstName(paymentDTO.getFirstName());
        payment.setLastName(paymentDTO.getLastName());
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Long id, PaymentDTO paymentDTO) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setCardNumber(paymentDTO.getCardNumber());
            payment.setCvv(paymentDTO.getCvv());
            payment.setExpDate(paymentDTO.getExpDate());
            payment.setPaymentDate(paymentDTO.getPaymentDate());
            payment.setFirstName(paymentDTO.getFirstName());
            payment.setLastName(paymentDTO.getLastName());
        return paymentRepository.save(payment);
        } else {
            throw new ResourceNotFoundException("Payment with ID " + id + " not found");
        }
    }

    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID " + id + " not found"));

        paymentRepository.delete(payment);
    }
}