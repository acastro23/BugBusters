package com.bugbusters.swe_backend;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class TestCase3 {
    
    @Test
    void allCreditCardDetailsValid(){
        assertTrue(validateCard("1111222233334444", 111, "James Smith", 11, 2040));
    }

    @Test
    void validAmexCard(){
        assertTrue(validateCard("3111222233334444", 4321, "Barack Obama", 12, 2024));
    }

    @Test
    void notValidAmexCard(){
        assertFalse(validateCard("3111222233334444", 431, "Aidan Tremper", 11, 2040));
    }

    @Test
    void cardExpiredByAFewMonths(){
        assertFalse(validateCard("1111222233334444", 111, "James Smith", 4, 2024));
    }

    @Test
    void everyDetailIsWrong(){
        assertFalse(validateCard("1123334444", 111222, "J", 2, 1930));
    }

    public static boolean validateCard(String cardNumber, int cvv, String name, int expirationMonth, int expirationYear) {
		//Variables for current date
		LocalDate today = LocalDate.now();
		int currentMonth = today.getMonthValue();
		int currentYear = today.getYear();
		//Check to see if cardNumber is 16 digits
		if(cardNumber.length()!=16) {
			return false;
        }
			
		//Check to make sure CVV is 4 digits if AMEX card
		if((cardNumber.substring(0,1)).equals("3")) {
			if(cvv<1000||cvv>9999) {
                return false;
			}
		//Else, card is not AMEX and CVV should be 3 digits
		}else {
			if(cvv<100||cvv>999) {
                return false;
			}
		}
				
		//Make sure name is more than 1 character long
		if(name.length()<2) {
			return false;
		}

		//Check to make sure card is not expired
		if(expirationYear<currentYear || (expirationYear==currentYear && expirationMonth<currentMonth)) {
			return false;
		}
        //Card is good
        return true;
    }
}