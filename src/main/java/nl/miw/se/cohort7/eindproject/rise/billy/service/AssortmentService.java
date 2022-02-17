package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AssortmentService {

    List<CategoryDto> findAllCategories();

    void saveCategory(CategoryDto categoryDto);
}
