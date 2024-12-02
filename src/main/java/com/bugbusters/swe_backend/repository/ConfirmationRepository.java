package com.bugbusters.swe_backend.repository;

import com.bugbusters.swe_backend.entity.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Confirmation findByBookingID(Long bookingID);

    /*
    AC1202 -- There was a bug when attempting to fetch booking details. If a guest had multiple bookings, the database would return all details that matched the guest
    name, so I had to fix it to only fetch details by a unique confirmation number, also had to alter the table because I did not make the confNum unique initially.


    Now no errors should return if on the summaryconfirmation.html page when entering a confirmation number, exception being if the confNum does not exist.
    */
    @Query("SELECT c FROM Confirmation c WHERE c.confNum = :confNum")
    Optional<Confirmation> findUniqueByConfNum(@Param("confNum") String confNum);




}
