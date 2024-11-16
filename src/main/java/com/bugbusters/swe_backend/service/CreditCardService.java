package com.bugbusters.swe_backend.service;

import com.bugbusters.swe_backend.dto.CreditCardDTO;
import com.bugbusters.swe_backend.entity.CreditCard;
import com.bugbusters.swe_backend.exception.ResourceNotFoundException;
import com.bugbusters.swe_backend.repository.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;
    
    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }
    
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }
    
    public Optional<CreditCard> getCreditCardById(Long id){
        return creditCardRepository.findById(id);
    }
    
    public CreditCard saveCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    public CreditCard createCreditCard(CreditCardDTO creditCardDTO){
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(creditCardDTO.getCardNumber());
        creditCard.setCvv(creditCardDTO.getCvv());
        creditCard.setFirstName(creditCardDTO.getFirstName());
        creditCard.setLastName(creditCardDTO.getLastName());
        creditCard.setExpDate(creditCardDTO.getExpDate());
        return creditCardRepository.save(creditCard);
    }

    public CreditCard updateCreditCard(Long id, CreditCardDTO creditCardDTO) {
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(id);
        if (optionalCreditCard.isPresent()) {
            CreditCard creditCard = optionalCreditCard.get();
            creditCard.setCardNumber(creditCardDTO.getCardNumber());
            creditCard.setCvv(creditCardDTO.getCvv());
            creditCard.setFirstName(creditCardDTO.getFirstName());
            creditCard.setLastName(creditCardDTO.getLastName());
            creditCard.setExpDate(creditCardDTO.getExpDate());
        return creditCardRepository.save(creditCard);
        } else {
            throw new ResourceNotFoundException("Guest with ID " + id + " not found");
        }
    }

    public void deleteCreditCard(Long id) {
        CreditCard creditCard = creditCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credit card with ID " + id + " not found"));

        creditCardRepository.delete(creditCard);
    }
}
