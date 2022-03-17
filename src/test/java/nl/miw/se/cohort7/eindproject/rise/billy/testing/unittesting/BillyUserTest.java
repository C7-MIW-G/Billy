package nl.miw.se.cohort7.eindproject.rise.billy.testing.unittesting;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillyUserTest {

    @Test
    void testSetRandomPassword() {
        // Arrange
        BillyUser billyUser = new BillyUser();

        // Activate
        billyUser.setRandomPassword();

        // Assert
        assertNotNull(billyUser.getPassword());
    }

    @Test
    void testRandomizationIsDifferent() {
        // Arrange
        BillyUser billyUser1 = new BillyUser();
        BillyUser billyUser2 = new BillyUser();

        // Activate
        billyUser1.setRandomPassword();
        billyUser2.setRandomPassword();

        // Assert
        assertNotEquals(billyUser1.getPassword(), billyUser2.getPassword());
    }

    @Test
    void testPasswordLength() {
        // Arrange
        BillyUser billyUser = new BillyUser();

        // Activate
        billyUser.setRandomPassword();

        // Assert
        assertEquals(BillyUser.RANDOM_PASSWORD_LENGTH, billyUser.getPassword().length());
    }

    @Test
    void testIllegalCharacters() {
        // Arrange
        BillyUser billyUser = new BillyUser();
        boolean illegalCharacterFound = false;
        int nPasswords = 5; // with 5 passwords of 64 characters it is aprrox. 97% (= (93/94)^(5*64) ) certain that a specific character was not in the selection pool.
        char firstLegalAsciiChar = 33; // 33 is the value of the '!' character
        char lastLegalAsciiChar = 126; // 126 is the value of the '~' character


        // Activate
        for (int i = 0; i < nPasswords; i++) {
            billyUser.setRandomPassword();
            for (int j = 0; j < billyUser.getPassword().length(); j++) {
                if (billyUser.getPassword().charAt(j) < firstLegalAsciiChar
                        || billyUser.getPassword().charAt(j) > lastLegalAsciiChar) {
                    illegalCharacterFound = true;
                }
            }
        }

        // Assert
        assertFalse(illegalCharacterFound);
    }
}
