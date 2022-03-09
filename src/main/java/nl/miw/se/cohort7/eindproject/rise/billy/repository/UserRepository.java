package nl.miw.se.cohort7.eindproject.rise.billy.repository;

import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<BillyUser, Long> {
    Optional<BillyUser> findByUsername(String username);
    List<BillyUser> findByUserRole(String role);
}
