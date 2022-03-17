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
public class BillyUserDto implements Comparable <BillyUserDto> {

    private static final int USER_DISPLAY_STRING_LENGTH = 20;

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
                return "fas fa-user-cog";
            case "ROLE_BARTENDER":
                return "fas fa-user-tie";
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

    public String getAccountBalanceEuroString() {
        return formatAsEuro(accountBalance);
    }

    public String getRemainingCreditDisplayString() {
        return formatAsEuro(accountBalance - maxCredit);
    }

    public String getCreditDisplayString() {
        return formatAsEuro(maxCredit * -1);
    }

    private String formatAsEuro(double amount) {
        return String.format("\u20ac %s%.2f", amount < 0 ? " -" : " ", Math.abs(amount));
    }

    public void payOrder(double amount) {
        accountBalance -= amount;
    }

    public void calculateNewCredit(double addCredit) {
        accountBalance += addCredit;
    }

    @Override
    public int compareTo(@org.jetbrains.annotations.NotNull BillyUserDto o) {
        return this.getName().toLowerCase().compareTo(o.getName().toLowerCase());
    }
}

