package nl.miw.se.cohort7.eindproject.rise.billy.service;


import nl.miw.se.cohort7.eindproject.rise.billy.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' doesn't exist"));
    }
}
