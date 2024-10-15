package com.bugbusters.swe_backend.service;

import com.bugbusters.swe_backend.dto.RoomDTO;
import com.bugbusters.swe_backend.entity.Room;
import com.bugbusters.swe_backend.exception.ResourceNotFoundException;
import com.bugbusters.swe_backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room createRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setFloor(roomDTO.getFloor());
        room.setType(roomDTO.getType());
        room.setAvailability(roomDTO.getAvailability());  // Updated here
        room.setDescription(roomDTO.getDescription());

        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, RoomDTO roomDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + id + " not found"));

        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setFloor(roomDTO.getFloor());
        room.setType(roomDTO.getType());
        room.setAvailability(roomDTO.getAvailability());  // Updated here
        room.setDescription(roomDTO.getDescription());

        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + id + " not found"));

        roomRepository.delete(room);
    }
}
