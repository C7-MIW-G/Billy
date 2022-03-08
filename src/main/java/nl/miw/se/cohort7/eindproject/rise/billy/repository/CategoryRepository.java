package nl.miw.se.cohort7.eindproject.rise.billy.repository;

import nl.miw.se.cohort7.eindproject.rise.billy.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(String name);
}
