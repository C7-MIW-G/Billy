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
import javax.validation.constraints.NotNull;
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

    @Id
    @GeneratedValue
    private long userId;

    @Column(nullable = false)
    private String userRole;

    @Size(min = 1, message = "Please enter a first name")
    @Size(max = 64, message = "The first name of the user should be less than 65 characters")
    @Column(nullable = false)
    private String firstname;

    @Size(min = 1, message = "Please enter a last name")
    @Size(max = 64, message = "The last name of the user should be less than 65 characters")
    @Column(nullable = false)
    private String lastname;

    @NotBlank
    @Email(message = "Please enter a valid email address")
    @Size(min = 1, message = "Please enter a email address")
    @Size(max = 64, message = "The email of the user should be less than 65 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @Size(min = 6, message = "Password should contain at least 6 characters")
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please provide a date.")
    private Date birthdate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority(userRole));

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
    

