package com.bugbusters.swe_backend.repository;

import com.bugbusters.swe_backend.entity.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {

}
