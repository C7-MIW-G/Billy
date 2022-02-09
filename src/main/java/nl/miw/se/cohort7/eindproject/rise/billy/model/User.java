package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * Describes a user who makes use of the application
 */

@Entity
@Getter
@Setter
public class User implements UserDetails {

    public static final int MIN_FIRST_NAME_LENGTH = 1;
    public static final String MIN_FIRST_NAME_LENGTH_MESSAGE = "Please enter a first name";

    public static final int MAX_FIRST_NAME_LENGTH = 64;
    public static final String MAX_FIRST_NAME_LENGTH_MESSAGE = "The first name of the user should be less than 65 characters";

    public static final int MIN_LAST_NAME_LENGTH = 1;
    public static final String MIN_LAST_NAME_LENGTH_MESSAGE = "Please enter a last name";

    public static final int MAX_LAST_NAME_LENGTH = 64;
    public static final String MAX_LAST_NAME_LENGTH_MESSAGE = "The last name of the user should be less than 65 characters";

    public static final int MIN_EMAIL_LENGTH = 1;
    public static final String MIN_EMAIL_LENGTH_MESSAGE = "Please enter a email address";

    public static final int MAX_EMAIL_LENGTH = 64;
    public static final String MAX_EMAIL_LENGTH_MESSAGE = "The email of the user should be less than 65 characters";

    public static final String INVALID_EMAIL_MESSAGE = "Invalid email, try again";

    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final String MIN_PASSWORD_LENGTH_MESSAGE = "Password should contain at least 6 characters";


    @Id
    @GeneratedValue
    private long userId;

    @NotBlank
    @Size(min = MIN_FIRST_NAME_LENGTH, message = MIN_FIRST_NAME_LENGTH_MESSAGE)
    @Size(max = MAX_FIRST_NAME_LENGTH, message = MAX_FIRST_NAME_LENGTH_MESSAGE)
    @Column(nullable = false)
    private String userRole;

    @Column(nullable = false)
    private String firstname;

    @NotBlank
    @Size(min = MIN_LAST_NAME_LENGTH, message = MIN_LAST_NAME_LENGTH_MESSAGE)
    @Size(max = MAX_LAST_NAME_LENGTH, message = MAX_LAST_NAME_LENGTH_MESSAGE)
    @Column(nullable = false)
    private String lastname;

    @NotBlank
    @Email(message = INVALID_EMAIL_MESSAGE)
    @Size(min = MIN_EMAIL_LENGTH, message = MIN_EMAIL_LENGTH_MESSAGE)
    @Size(max = MAX_EMAIL_LENGTH, message = MAX_EMAIL_LENGTH_MESSAGE)
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Size(min = MIN_PASSWORD_LENGTH, message = MIN_PASSWORD_LENGTH_MESSAGE)
    @Column(nullable = false)
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date birthdate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority("ROLE_BARTENDER"));

        return authorityList;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String fullNameDisplayString() {
        return String.format("%s %s", this.firstname, this.lastname);
    }
}
    

