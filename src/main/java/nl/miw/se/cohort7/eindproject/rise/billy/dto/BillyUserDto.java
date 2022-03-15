package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se.cohort7.eindproject.rise.billy.customConstraintValidation.UniqueUsername;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Calendar;
import javax.validation.constraints.NegativeOrZero;
import java.util.Date;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 11/02/2022
 * Dit is wat het programma doet.
 */

@Getter @Setter
@UniqueUsername
public class BillyUserDto {

    private static final int USER_DISPLAY_STRING_LENGTH = 20;
    public static final int MIN_AGE_FOR_PRODUCTS_OF_AGE = 18;

    private Long userId;

    @NotEmpty
    private String userRole;

    @Size(min = 1, message = "Please enter a name")
    @Size(max = 64, message = "The name of the user should be shorter than 65 characters")
    private String name;

    @NotBlank
    @Email(message = "Please enter a valid email address")
    @Size(min = 1, message = "Please enter an email address")
    @Size(max = 64, message = "The email of the user should be less than 65 characters")
    private String username;

    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide a date.")
    @Past(message = "Please select a date in the past.")
    private Date birthdate;

    private double accountBalance;

    @NegativeOrZero(message = "Please insert a negative number or 0")
    private double maxCredit;

    public String getUserRoleEmoticon() {
        switch (userRole) {
            case "ROLE_BAR MANAGER":
                return new String(Character.toChars(0x039c));
            case "ROLE_BARTENDER":
                return new String(Character.toChars(0x03a4));
            case "ROLE_CUSTOMER":
                return new String(Character.toChars(0x2800));
            default:
                return "";
        }
    }

    public String getUserRoleDisplayString() {
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

    public String getAccountBalanceDisplayString() {
        return String.format("%.2f", accountBalance);
    }

    public String getRemainingCreditDisplayString(){
        return formatAsEuro(accountBalance - maxCredit);
    }

    public String getCreditDisplayString() {
        return formatAsEuro(maxCredit * -1);
    }

    private String formatAsEuro(double amount) {
        return String.format("%s\u20ac %.2f", amount < 0 ? " -" : " ", Math.abs(amount));
    }

    public boolean canPayForOrder() {
        return maxCredit < accountBalance - BarOrderDto.activeOrder.calculateTotalOrderPrice();
    }

    public void payOrder(double amount) {
        accountBalance -= amount;
    }

    public void calculateNewCredit(double addCredit){
        accountBalance += addCredit;
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

    public boolean isUserEighteenPlus() {
        return getAge(this.birthdate) >= MIN_AGE_FOR_PRODUCTS_OF_AGE;
    }

}


