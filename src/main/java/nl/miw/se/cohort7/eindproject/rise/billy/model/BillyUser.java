package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
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
    public static final int USER_DISPLAY_STRING_LENGTH = 20;

    @Id
    @GeneratedValue
    private long userId;

    @Column(nullable = false)
    private String userRole;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

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
}
    

