package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.PasswordDto;

import java.util.List;


public interface UserService {

    BillyUserDto findByUserId(Long id);

    List<BillyUserDto> findAll();

    void saveNewUser(BillyUserDto billyUserDto);

    List<BillyUserDto> findUsersByRole(String role);

    void updateUser(BillyUserDto billyUserDto);

    void delete(Long userId);

    void updatePassword(PasswordDto passwordDto);

    boolean isUsernameUnique(BillyUserDto billyUserDto);

}

