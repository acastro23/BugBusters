package com.bugbusters.swe_backend.service;

import com.bugbusters.swe_backend.dto.RoomDTO;
import com.bugbusters.swe_backend.entity.Room;
import com.bugbusters.swe_backend.entity.RoomImage;
import com.bugbusters.swe_backend.exception.ResourceNotFoundException;
import com.bugbusters.swe_backend.repository.RoomImageRepository;
import com.bugbusters.swe_backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;

    public RoomService(RoomRepository roomRepository, RoomImageRepository roomImageRepository) {
        this.roomRepository = roomRepository;
        this.roomImageRepository = roomImageRepository;
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
        room.setAvailability(roomDTO.getAvailability());
        room.setDescription(roomDTO.getDescription());

        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, RoomDTO roomDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + id + " not found"));

        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setFloor(roomDTO.getFloor());
        room.setType(roomDTO.getType());
        room.setAvailability(roomDTO.getAvailability());
        room.setDescription(roomDTO.getDescription());

        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room with ID " + id + " not found"));

        roomRepository.delete(room);
    }


    public RoomImage addImageToRoom(Long roomId, String imageURL) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));

        RoomImage roomImage = new RoomImage();
        roomImage.setRoom(room);
        roomImage.setImageURL(imageURL);  // AC1103 -- ImageURL are google images (copy image address)

        return roomImageRepository.save(roomImage);
    }

    public List<RoomImage> getImagesForRoom(Long roomId) {
        return roomImageRepository.findByRoomRoomID(roomId);
    }

    public void deleteRoomImage(Long imageID) {
        roomImageRepository.deleteById(imageID);
    }
}
