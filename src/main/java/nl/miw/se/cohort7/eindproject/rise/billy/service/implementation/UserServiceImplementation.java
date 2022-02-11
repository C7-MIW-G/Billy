package nl.miw.se.cohort7.eindproject.rise.billy.service.implementation;

import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.UserRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * Date created: 07-02-2022
 * This service layer connects the front- and back-end of the user object
 */

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<BillyUser> findByUserId(Long id) {
        return userRepository.findById(id);
    }

    public Optional<BillyUser> findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public List<BillyUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(BillyUser billyUser) {
        userRepository.save(billyUser);
    }

    @Override
    public boolean mayWriteToDB(BillyUser billyUser) {
        Optional<BillyUser> optionalBillyUser = userRepository.findByUsername(billyUser.getUsername());
        if(optionalBillyUser.isEmpty()){
            return true;
        } else if (optionalBillyUser.get().equals(billyUser)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void delete(BillyUser billyUser) {
        userRepository.delete(billyUser);
    }
}
