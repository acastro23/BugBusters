package com.bugbusters.swe_backend.controller;

import com.bugbusters.swe_backend.dto.GuestDTO;
import com.bugbusters.swe_backend.entity.Guest;
import com.bugbusters.swe_backend.service.GuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
    GuestController handles Http request for the CRUD operations and creates endpoints, the actual CRUD operations are done in the GuestService class
*/

@RestController
@RequestMapping("/api/guests")      // base url for endpoints, this gets all guests from the guests table
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }

    /*
        Replace {id} with actual guestID when testing
    */

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long id) {
        Optional<Guest> guest = guestService.getGuestById(id);
        return guest.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Guest createGuest(@RequestBody Guest guest) {
        return guestService.saveGuest(guest);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long id, @RequestBody GuestDTO guestDTO) {
        Guest updatedGuest = guestService.updateGuest(id, guestDTO);
        return ResponseEntity.ok(updatedGuest);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return ResponseEntity.noContent().build();
    }
}
