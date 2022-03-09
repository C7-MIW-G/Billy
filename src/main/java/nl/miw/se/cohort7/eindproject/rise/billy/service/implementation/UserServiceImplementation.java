package nl.miw.se.cohort7.eindproject.rise.billy.service.implementation;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import nl.miw.se.cohort7.eindproject.rise.billy.model.ChangePassword;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.UserRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.service.DtoConverter.BillyUserDtoConverter;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private BillyUserDtoConverter billyUserDtoConverter = new BillyUserDtoConverter();
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BillyUserDto findByUserId(Long id) {
        Optional<BillyUser> billyUser = userRepository.findById(id);
        if (billyUser.isEmpty()) {
            return null;
        }
        return billyUserDtoConverter.convertToDto(billyUser.get());
    }

    @Override
    public List<BillyUserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(billyUserDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BillyUserDto> findUsersByRole(String role) {
        return userRepository.findByUserRole(role)
                .stream()
                .map(billyUserDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveNewUser(BillyUserDto billyUserDto) {
        BillyUser newUser = billyUserDtoConverter.toBillyUser(billyUserDto);

        if (newUser.getUserRole().equals("Customer")) {
            newUser.setRandomPassword();
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userRepository.save(newUser);
    }

    @Override
    public void updateUser(BillyUserDto billyUserDto) {
        Optional<BillyUser> optionalBillyUser = userRepository.findById(billyUserDto.getUserId());
        if (optionalBillyUser.isEmpty()) {
            return;
        }
        BillyUser userToUpdate = billyUserDtoConverter.toBillyUser(billyUserDto);
        userToUpdate.setPassword(optionalBillyUser.get().getPassword());
        userRepository.save(userToUpdate);
    }

    @Override
    public void updatePassword(ChangePassword changePassword) {
        Optional<BillyUser> optionalBillyUser = userRepository.findById(changePassword.getUserId());
        if (optionalBillyUser.isEmpty()) {
            return;
        }
        BillyUser userWithNewPassword = optionalBillyUser.get();

        userWithNewPassword.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userRepository.save(userWithNewPassword);
    }

    @Override
    public void delete(Long userId) {
        Optional<BillyUser> billyUser = userRepository.findById(userId);
        billyUser.ifPresent(user -> userRepository.delete(user));
    }

    @Override
    public boolean isUsernameUnique(BillyUserDto billyUserDto) {
        Optional<BillyUser> optionalBillyUser = userRepository.findByUsername(billyUserDto.getUsername());

        if (optionalBillyUser.isEmpty()) {
            return true;
        } else if (billyUserDto.getUserId() == null) {
            // New user, so he/she may not register with this username.
            return false;
        } else if (optionalBillyUser.get().getUserId() == billyUserDto.getUserId()) {
            // In this case he/she is the same user, so may update
            return true;
        } else {
            return false;
        }
    }
}
