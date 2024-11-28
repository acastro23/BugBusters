package com.bugbusters.swe_backend.repository;

import com.bugbusters.swe_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByGuest_GuestID(Long guestID);

}