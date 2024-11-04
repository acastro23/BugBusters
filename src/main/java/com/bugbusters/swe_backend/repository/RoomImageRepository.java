package com.bugbusters.swe_backend.repository;

import com.bugbusters.swe_backend.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
    List<RoomImage> findByRoomRoomID(Long roomId);
}
