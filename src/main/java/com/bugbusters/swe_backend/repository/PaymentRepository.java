package com.bugbusters.swe_backend.repository;

import com.bugbusters.swe_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByGuest_GuestID(Long guestID);

    //1202 -- This query also helps fetch details when guest wants to see their booking details using the confirmation number.
    @Query("SELECT p FROM Payment p WHERE p.guest.guestID = :guestID AND p.booking.bookingID = :bookingID")
    Optional<Payment> findByGuestAndBooking(@Param("guestID") Long guestID, @Param("bookingID") Long bookingID);


}