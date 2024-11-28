package com.bugbusters.swe_backend.service;

import com.bugbusters.swe_backend.dto.GuestDTO;
import com.bugbusters.swe_backend.entity.Guest;
import com.bugbusters.swe_backend.exception.ResourceNotFoundException;
import com.bugbusters.swe_backend.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// manages the CRUD operations logic for the guests. not actually interacting with the DB itself(handled by GuestRepository).

// yes
@Service
public class GuestService {
    private final GuestRepository guestRepository;

    //
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }


    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }


    public Optional<Guest> getGuestById(Long id) {
        return guestRepository.findById(id);
    }


    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }


    public Guest createGuest(GuestDTO guestDTO) {
        if (guestRepository.findByEmail(guestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("A guest with this email already exists.");
        }

        Guest guest = new Guest();
        guest.setFName(guestDTO.getFname());
        guest.setLName(guestDTO.getLname());
        guest.setEmail(guestDTO.getEmail());
        guest.setPhoneNumber(guestDTO.getPhoneNumber());

        return guestRepository.save(guest);
    }


    /*
        Subject to change, when updating a guests, all fields must be updated no matter if you are only changing one field.
        updated guest object handled by GuestDTO,

        If a guest is not found, a ResourceNotFoundException will be thrown
    */
    public Guest updateGuest(Long id, GuestDTO guestDTO) {
        Optional<Guest> optionalGuest = guestRepository.findById(id);
        if (optionalGuest.isPresent()) {
            Guest guest = optionalGuest.get();
            guest.setFName(guestDTO.getFname());
            guest.setLName(guestDTO.getLname());
            guest.setEmail(guestDTO.getEmail());
            guest.setPhoneNumber(guestDTO.getPhoneNumber());
            return guestRepository.save(guest);
        } else {
            throw new ResourceNotFoundException("Guest with ID " + id + " not found");
        }
    }
}
