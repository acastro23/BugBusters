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
    private final GuestRepository myGuestRepository;

    //
    public GuestService(GuestRepository guestRepository) {
        this.myGuestRepository = guestRepository;
    }


    public List<Guest> getAllGuests() {
        return myGuestRepository.findAll();
    }


    public Optional<Guest> getGuestById(Long id) {
        return myGuestRepository.findById(id);
    }


    public void deleteGuest(Long id) {
        myGuestRepository.deleteById(id);
    }


    public Guest createOrReuseGuest(GuestDTO guestDTO) {
        Optional<Guest> existingGuest = myGuestRepository.findByEmail(guestDTO.getEmail());
        if (existingGuest.isPresent()) {
            return existingGuest.get();
        }
        if (guestDTO == null) {
            throw new IllegalArgumentException("Guest information is required to create a new guest.");
        } else if (!guestDTO.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        } else if (!guestDTO.getPhoneNumber().matches("^\\d{3}-\\d{3}-\\d{4}$")) {
            throw new IllegalArgumentException("Invalid phone number.");
        } else if (!guestDTO.getFname().matches("^[A-Za-z]+(?:[-' ][A-Za-z]+)*$") || !guestDTO.getLname().matches("^[A-Za-z]+(?:[-' ][A-Za-z]+)*$")) {
            throw new IllegalArgumentException(("The name may only contains letters."));
        }
        Guest myGuest = new Guest();
        myGuest.setFName(guestDTO.getFname());
        myGuest.setLName(guestDTO.getLname());
        myGuest.setEmail(guestDTO.getEmail());
        myGuest.setPhoneNumber(guestDTO.getPhoneNumber());
        return myGuestRepository.save(myGuest);
    }


    /*
        Subject to change, when updating a guests, all fields must be updated no matter if you are only changing one field.
        updated guest object handled by GuestDTO,

        If a guest is not found, a ResourceNotFoundException will be thrown
    */
    public Guest updateGuest(Long id, GuestDTO guestDTO) {
        Optional<Guest> optionalGuest = myGuestRepository.findById(id);
        if (optionalGuest.isPresent()) {
            Guest guest = optionalGuest.get();
            guest.setFName(guestDTO.getFname());
            guest.setLName(guestDTO.getLname());
            guest.setEmail(guestDTO.getEmail());
            guest.setPhoneNumber(guestDTO.getPhoneNumber());
            return myGuestRepository.save(guest);
        } else {
            throw new ResourceNotFoundException("Guest with ID " + id + " not found");
        }
    }
}
