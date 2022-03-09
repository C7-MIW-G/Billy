package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se.cohort7.eindproject.rise.billy.customConstraintValidation.UniqueUsername;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 11/02/2022
 * Dit is wat het programma doet.
 */

@Getter @Setter
@UniqueUsername
public class BillyUserDto {

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

    public String getUserRoleDisplayString() {
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

    public String getAccountBalanceDisplayString() {
        return formatAsEuro(accountBalance);
    }

    public String getCreditDisplayString() {
        return formatAsEuro(maxCredit);
    }

    private String formatAsEuro(double amount) {
        return String.format("%s\u20ac %.2f", amount < 0 ? " -" : " ", Math.abs(amount));
    }

    public boolean canPayForOrder() {
        return maxCredit > accountBalance - BarOrderDto.activeOrder.calculateTotalOrderPrice();
    }

    public void payOrder(double amount) {
        accountBalance -= amount;
    }
}
