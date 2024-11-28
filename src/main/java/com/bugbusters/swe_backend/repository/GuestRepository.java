package com.bugbusters.swe_backend.repository;

import com.bugbusters.swe_backend.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    // the class that actually interacts with the database using JPA.
    Optional<Guest> findByEmail(String email);
}
