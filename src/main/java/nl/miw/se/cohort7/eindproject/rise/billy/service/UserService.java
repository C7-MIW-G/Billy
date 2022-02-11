package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<BillyUser> findByUserId(Long id);

    List<BillyUser> findAll();

    void save(BillyUser billyUser);

    boolean usernameIsUnique(BillyUser billyUser);

    boolean isIDUnique(BillyUser billyUser);

    void delete(BillyUser billyUser);
}
