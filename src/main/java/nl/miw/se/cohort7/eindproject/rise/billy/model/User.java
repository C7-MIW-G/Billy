package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * Describes a user who makes use of the application
 */

@Entity
@Getter
@Setter
public class User {

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DEFAULT_BIRTHDATE = "01-01-1970";
    @Id
    @GeneratedValue
    private long userId;

    private String userName;

    private LocalDateTime dateOfBirth;

    private double credit;

    public String birthdayDisplayString() {
            return String.format("%s", this.dateOfBirth.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
        }
    }
