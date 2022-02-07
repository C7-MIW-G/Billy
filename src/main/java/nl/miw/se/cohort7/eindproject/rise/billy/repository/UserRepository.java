package nl.miw.se.cohort7.eindproject.rise.billy.repository;

import nl.miw.se.cohort7.eindproject.rise.billy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
