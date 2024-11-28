package com.bugbusters.swe_backend.repository;

import com.bugbusters.swe_backend.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE " +
            "(:availability IS NULL OR r.availability = :availability) AND " +
            "(:minPrice IS NULL OR r.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR r.price <= :maxPrice) AND " +
            "(:type IS NULL OR r.type = :type)")
    List<Room> searchRooms(@Param("availability") Boolean availability,
                           @Param("minPrice") BigDecimal minPrice,
                           @Param("maxPrice") BigDecimal maxPrice,
                           @Param("type") String type);


    List<Room> findByType(String type);

    @Query("SELECT r FROM Room r WHERE r.availability = true " +
            "AND (:type IS NULL OR r.type = :type) " +
            "AND r.roomID NOT IN (SELECT b.myRoom.roomID FROM Booking b " +
            "WHERE :checkIn < b.checkOutTime AND :checkOut > b.checkInTime)")
    List<Room> findAvailableRooms(@Param("checkIn") LocalDateTime checkIn,
                                  @Param("checkOut") LocalDateTime checkOut,
                                  @Param("type") String type);



}
