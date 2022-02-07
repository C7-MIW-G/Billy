package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUserId(Long id);

    List<User> findAll();

    void save(User user);

    void delete(User user);
}
