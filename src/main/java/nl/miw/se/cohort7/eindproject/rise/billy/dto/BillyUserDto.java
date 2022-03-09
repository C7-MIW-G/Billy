package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import javax.validation.constraints.NegativeOrZero;
import java.util.Date;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 11/02/2022
 * Dit is wat het programma doet.
 */

@Getter @Setter
public class BillyUserDto {

    private static final int USER_DISPLAY_STRING_LENGTH = 20;
    public static final int MIN_AGE_FOR_PRODUCTS_OF_AGE = 18;

    private Long userId;

    private String userRole;

    private String name;

    private String username;

    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    private double accountBalance;

    @NegativeOrZero(message = "Please insert a negative number or 0")
    private double maxCredit;

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

    public String userRoleDisplayStringDetails() {
        switch (userRole) {
            case "ROLE_BAR MANAGER":
                return "Bar manager";
            case "ROLE_BARTENDER":
                return "Bartender";
            case "ROLE_CUSTOMER":
                return "Customer";
            default:
                return "Unknown";
        }
    }

    public String getAccountBalanceDisplayString(double accountBalance) {

        return String.format("%s\u20ac %.2f", accountBalance < 0 ? " -" : " ", Math.abs(accountBalance));
    }

    public String getCreditDisplayString(double maxCredit) {
        return String.format("%s\u20ac %.2f", maxCredit < 0 ? " -" : " ", Math.abs(maxCredit * -1));
    }

    public double getRemainingCredit(double accountBalance, double maxCredit){
        return (maxCredit - accountBalance);
    }

    public String getRemainingCreditDisplayString(){
        return String.format("\u20ac %.2f",
                (getRemainingCredit(accountBalance, maxCredit) * -1));
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

    public double getAccountBalanceWithActiveOrder() {
        return (accountBalance + (BarOrderDto.activeOrder.calculateTotalOrderPrice() * -1));
    }

    public static int getAge(Date dateOfBirth) {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        int age = 0;

        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        return age;
    }

    public boolean isOfAge () {
        return getAge(this.birthdate) >= MIN_AGE_FOR_PRODUCTS_OF_AGE;
    }

}


