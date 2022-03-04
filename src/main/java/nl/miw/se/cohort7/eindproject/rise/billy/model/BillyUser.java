package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.Date;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * Describes a user who makes use of the application
 */

@Entity
@Getter
@Setter
public class BillyUser {

    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    public static final int RANDOM_PASSWORD_LENGTH = 64;
    public static final int RANDOMIZATION_POOL_LENGTH = 94;
    public static final int START_USABLE_ASCII_CHARACTERS = 33;

    @Id
    @GeneratedValue
    private long userId;

    @Column(nullable = false)
    private String userRole;

    @Size(min = 1, message = "Please enter a name")
    @Size(max = 64, message = "The name of the user should be shorter than 65 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Email(message = "Please enter a valid email address")
    @Size(min = 1, message = "Please enter a email address")
    @Size(max = 64, message = "The email of the user should be less than 65 characters")
    @Column(nullable = false, unique = true)
    private String username;

    //Validation in Controller because customer accounts have auto generated passwords
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide a date.")
    private Date birthdate;

    private double accountBalance;

    private double maxCredit;

    public void setRandomPassword() {
        StringBuilder randomPasswordBuilder = new StringBuilder();
        for (int i = 0; i < RANDOM_PASSWORD_LENGTH; i++) {
            randomPasswordBuilder.append(getRandomChar());
        }
        this.setPassword(randomPasswordBuilder.toString());
    }

    private char getRandomChar() {
        int randomInt = (int) (Math.random() * RANDOMIZATION_POOL_LENGTH + START_USABLE_ASCII_CHARACTERS);
        return (char) randomInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillyUser billyUser = (BillyUser) o;
        return userId == billyUser.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public void payFromBalance(double amount) {
        if (amount < 0) {
            return;
        }
        this.accountBalance -= amount;
    }
}
    

