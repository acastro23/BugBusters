package com.bugbusters.swe_backend.repository;

import com.bugbusters.swe_backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // You can add custom query methods if needed
}
