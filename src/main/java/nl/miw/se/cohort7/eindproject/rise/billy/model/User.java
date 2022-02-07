package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * Describes a user who makes use of the application
 */

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private long userId;

    private String userName;

    private LocalDateTime dateOfBirth;

    public String getCreditDisplayString(double credit){
        return String.format("\u20ac %.2f", credit);
    }

}
