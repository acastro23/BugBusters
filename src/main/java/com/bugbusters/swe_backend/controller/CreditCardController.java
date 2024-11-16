package com.bugbusters.swe_backend.controller;

import com.bugbusters.swe_backend.dto.CreditCardDTO;
import com.bugbusters.swe_backend.entity.CreditCard;
import com.bugbusters.swe_backend.service.CreditCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
public class CreditCardController {
    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService){
        this.creditCardService = creditCardService;
    }

    @GetMapping
    public List<CreditCard> getAllCreditCards(){
        return creditCardService.getAllCreditCards();
    }


    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable Long id) {
        Optional<CreditCard> creditCard = creditCardService.getCreditCardById(id);
        return creditCard.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public CreditCard createCreditCard(@RequestBody CreditCard creditCard) {
        return creditCardService.saveCreditCard(creditCard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCard> updateCreditCard(@PathVariable Long id, @RequestBody CreditCardDTO creditCardDTO) {
        CreditCard updatedCreditCard = creditCardService.updateCreditCard(id, creditCardDTO);
        return ResponseEntity.ok(updatedCreditCard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable Long id) {
        creditCardService.deleteCreditCard(id);
        return ResponseEntity.noContent().build();
    }
}
