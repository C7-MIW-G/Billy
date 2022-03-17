package nl.miw.se.cohort7.eindproject.rise.billy.testing.unittests;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BillyUserTestLars {

    @Test
    void testPayOrder() {
        // Arrange
        BillyUserDto testUser = new BillyUserDto();
        testUser.setAccountBalance(50.00);
        double orderPrice = 20.00;

        // Activate
        testUser.payOrder(orderPrice);

        // Assert
        assertEquals((50 - 20), testUser.getAccountBalance());
    }

    @Test
    void testCalculateNewCredit() {
        // Arrange
        BillyUserDto testUser = new BillyUserDto();
        testUser.setAccountBalance(10);
        double topUp = 20;

        // Activate
        testUser.calculateNewCredit(topUp);

        // Assert
        assertEquals((10 + 20), testUser.getAccountBalance());
    }
}
