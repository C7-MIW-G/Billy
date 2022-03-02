package nl.miw.se.cohort7.eindproject.rise.billy.service.implementation;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUserPrincipal;
import nl.miw.se.cohort7.eindproject.rise.billy.model.ChangePassword;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.UserRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public BillyUserDto findByUserId(Long id) {
        Optional<BillyUser> billyUser = userRepository.findById(id);
        if (billyUser.isEmpty()) {
            return null;
        }
        return convertToDto(billyUser.get());
    }

    public Optional<BillyUser> findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public List<BillyUserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BillyUserDto convertToDto(BillyUser billyUser) {
        BillyUserDto billyUserDto = new BillyUserDto();

        billyUserDto.setUserId(billyUser.getUserId());
        billyUserDto.setUserRole(billyUser.getUserRole());
        billyUserDto.setName(billyUser.getName());
        billyUserDto.setUsername(billyUser.getUsername());
        billyUserDto.setBirthdate(billyUser.getBirthdate());
        billyUserDto.setAccountBalance(billyUser.getAccountBalance());

        return billyUserDto;
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
    public void delete(Long userId) {
        Optional<BillyUser> billyUser = userRepository.findById(userId);
        billyUser.ifPresent(user -> userRepository.delete(user));
    }

    @Override
    public void updatePassword(ChangePassword changePassword) {
        Optional<BillyUser> billyUser = userRepository.findById(changePassword.getUserId());
        if (billyUser.isEmpty()) {
            return;
        }
        BillyUser billyUser1 = billyUser.get();
        billyUser1.setPassword(changePassword.getNewPassword());
        userRepository.save(billyUser1);
    }

    @Override
    public void subtractFromBalance(Long userId, double amount) {
        Optional<BillyUser> billyUserOpt = userRepository.findById(userId);
        if (billyUserOpt.isEmpty()) {
            return;
        }
        BillyUser billyUser = billyUserOpt.get();
        billyUser.payFromBalance(amount);
        userRepository.save(billyUser);
    }
}
