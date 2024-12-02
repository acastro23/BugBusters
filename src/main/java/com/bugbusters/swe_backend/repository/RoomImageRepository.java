package com.bugbusters.swe_backend.repository;

import com.bugbusters.swe_backend.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//AC1202 -- The roomImage stuff became irrelevant for the final presentation, but I will keep them for future enhancements to the project

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
    List<RoomImage> findByRoomRoomID(Long roomId);
}
