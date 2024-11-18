package com.bugbusters.swe_backend.controller;

import com.bugbusters.swe_backend.dto.RoomDTO;
import com.bugbusters.swe_backend.entity.Room;
import com.bugbusters.swe_backend.entity.RoomImage;
import com.bugbusters.swe_backend.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomService.getRoomById(id);
        return room.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Room createRoom(@Valid @RequestBody RoomDTO roomDTO) {
        return roomService.createRoom(roomDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomDTO roomDTO) {
        Room updatedRoom = roomService.updateRoom(id, roomDTO);
        return ResponseEntity.ok(updatedRoom);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }


    //AC1103 -- This section has endpoints for roomImages --> Check RoomImage entity to look at the attributes, just has one besides the keys
    @PostMapping("/{roomId}/images")
    public ResponseEntity<RoomImage> addImageToRoom(@PathVariable Long roomId,
                                                    @RequestParam String imageURL) {
        RoomImage roomImage = roomService.addImageToRoom(roomId, imageURL);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomImage);
    }

    //AC1103 -- get all images for a specific room
    @GetMapping("/{roomId}/images")
    public ResponseEntity<List<RoomImage>> getImagesForRoom(@PathVariable Long roomId) {
        List<RoomImage> roomImages = roomService.getImagesForRoom(roomId);
        return ResponseEntity.ok(roomImages);
    }

    //AC1103 -- delete image by its ID
    @DeleteMapping("/images/{imageID}")
    public ResponseEntity<Void> deleteRoomImage(@PathVariable Long imageID) {
        roomService.deleteRoomImage(imageID);
        return ResponseEntity.noContent().build();
    }
}
