package com.bugbusters.swe_backend.repository;

import com.bugbusters.swe_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
    AC1019 -- This class is a repository class, so its purpose is to interact with the booking table in the database, so
    we actually query the database to check if a room is available here
*/

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT booking FROM Booking booking WHERE booking.myRoom.roomID = :roomID AND " +
            "(booking.checkInTime <= :checkOut AND booking.checkOutTime >= :checkIn)")
    List<Booking> findBookings(@Param("roomID") Long roomID,
                               @Param("checkIn") LocalDateTime checkIn,
                               @Param("checkOut") LocalDateTime checkOut);

    //AC1124 -- to update a booking, we also need a query statement for that
    @Query("SELECT booking FROM Booking booking WHERE booking.myRoom.roomID = :roomID " +
            "AND (booking.checkInTime <= :checkOut AND booking.checkOutTime >= :checkIn) " +
            "AND booking.bookingID <> :bookingID")
    List<Booking> findBookingsForUpdate(@Param("roomID") Long roomID, @Param("checkIn") LocalDateTime checkIn,
                                        @Param("checkOut") LocalDateTime checkOut, @Param("bookingID") Long bookingID);


    @Query("SELECT b FROM Booking b WHERE b.myRoom.roomID = :roomID " +
            "AND (:checkIn < b.checkOutTime AND :checkOut > b.checkInTime)")
    List<Booking> findOverlappingBookings(@Param("roomID") Long roomID,
                                          @Param("checkIn") LocalDateTime checkIn,
                                          @Param("checkOut") LocalDateTime checkOut);

}
