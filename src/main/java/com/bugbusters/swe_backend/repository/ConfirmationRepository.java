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

    @Query("SELECT DISTINCT c FROM Confirmation c WHERE c.confNum = :confNum")
    Optional<Confirmation> findUniqueByConfNum(@Param("confNum") String confNum);



}
