package nl.miw.se.cohort7.eindproject.rise.billy.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Describes the current loggedin user
 */
public class BillyUserPrincipal implements UserDetails {

    private BillyUser billyUser;

    public BillyUserPrincipal(BillyUser billyUser) {
        this.billyUser = billyUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority(billyUser.getUserRole()));
        authorityList.add(new SimpleGrantedAuthority("ROLE_NOT_ADMIN")); ///remove when there is no longer a admin
        return authorityList;
    }

    @Override
    public String getPassword() {
        return billyUser.getPassword();
    }

    @Override
    public String getUsername() {
        return billyUser.getUsername();
    }

    public String getDisplayName(){
        return billyUser.getName();
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
}
