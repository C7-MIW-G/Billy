package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 11/02/2022
 * Dit is wat het programma doet.
 */

@Getter @Setter
public class BillyUserDto {

    private static final int USER_DISPLAY_STRING_LENGTH = 20;

    private Long userId;

    private String userRole;

    private String name;

    private String username;

    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    private double accountBalance;

    public String userRoleDisplayString() {
        switch (userRole) {
            case "ROLE_BAR MANAGER":
                return "Bar manager";
            case "ROLE_BARTENDER":
                return "Bartender";
            case "ROLE_CUSTOMER":
                return " ";
            default:
                return "Unknown";
        }
    }

    public String getAccountBalanceDisplayString(double accountBalance) {

        return String.format("%s\u20ac %.2f", accountBalance < 0 ? " -" : " ", Math.abs(accountBalance));
    }


    public void payFromBalance(double amount) {
        if (amount < 0) {
            return;
        }
        this.accountBalance -= amount;
    }

    public void makeDeposit(double amount) {
        if (amount < 0) {
            return;
        }
        this.accountBalance += amount;

    }

    public String getUserDisplayString() {
        StringBuilder userDisplayStringBuilder = new StringBuilder();

        for (int i = 0; i < USER_DISPLAY_STRING_LENGTH; i++) {
            if (i < this.name.length()) {
                userDisplayStringBuilder.append(this.name.charAt(i));
            } else {
                userDisplayStringBuilder.append("_");
            }
        }

        userDisplayStringBuilder.append(this.getAccountBalanceDisplayString(accountBalance));

        return userDisplayStringBuilder.toString();
    }
}


