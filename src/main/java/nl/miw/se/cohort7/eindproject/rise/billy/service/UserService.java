package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import nl.miw.se.cohort7.eindproject.rise.billy.model.ChangePassword;

import java.util.List;

public interface UserService {

    BillyUserDto findByUserId(Long id);

    List<BillyUserDto> findAll();

    void save(BillyUser billyUser);

    boolean mayWriteToDB(BillyUser billyUser);

    void delete(Long userId);

    void updatePassword(ChangePassword changePassword);
}
