package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 11/02/2022
 * Dit is wat het programma doet.
 */

@Getter @Setter
public class BillyUserDto {

    private Long userId;

    private String userRole;

    private String name;

    private String username;

    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    public String userRoleDisplayString() {
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

}
