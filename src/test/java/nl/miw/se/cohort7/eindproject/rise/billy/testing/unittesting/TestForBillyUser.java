package nl.miw.se.cohort7.eindproject.rise.billy.testing.unittesting;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.AddCreditDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * auditor: Joris van der Bij Email: j.vanderbij@hanze.st.nl
 * Unit testing
 */
public class TestForBillyUser {

    @Test
    void testCheckUserRole() {
        // Arrange
        BillyUserDto billyUserdto = new BillyUserDto();

        // Activate
        billyUserdto.setUserRole("ROLE_BAR MANAGER");

        // Assert
        assertEquals("ROLE_BAR MANAGER", billyUserdto.getUserRole());
    }

    @Test
    void testGetPriceDisplayString() {
        // Arrange
        ProductDto productDto = new ProductDto();

        // Activate
        productDto.setProductPrice(5);

        // Assert
        assertEquals("5,00", productDto.getPriceDisplayString());
    }

    @Test
    void TestAddCredit() {
        // Arrange
        AddCreditDto addCreditDto = new AddCreditDto();

        // Activate
        addCreditDto.setValue(50);

        // Assert
        assertEquals(50, addCreditDto.getValue());
    }
}



