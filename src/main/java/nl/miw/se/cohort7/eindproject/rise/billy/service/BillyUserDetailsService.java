package nl.miw.se.cohort7.eindproject.rise.billy.service;


import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUserPrincipal;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * [omschrijving van code]
 */

@Service
public class BillyUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public BillyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<BillyUser> optionalBillyUser = userRepository.findByUsername(username);
        if (optionalBillyUser.isEmpty()){
            throw new UsernameNotFoundException("User '" + username + "' doesn't exist");
        }
        return new BillyUserPrincipal(optionalBillyUser.get());
    }
}
