package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import nl.miw.se.cohort7.eindproject.rise.billy.model.ChangePassword;

import java.util.List;
import java.util.Optional;

public interface UserService {

    BillyUserDto findByUserId(Long id);

    List<BillyUserDto> findAll();

    void save(BillyUser billyUser);

    boolean mayWriteToDB(BillyUser billyUser);

    void delete(Long userId);

    boolean confirmPassword(ChangePassword changePassword);

    void updatePassword(ChangePassword changePassword);
}
