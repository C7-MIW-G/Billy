package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.MinProductAjaxDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;

import java.util.List;

public interface AjaxAssortmentService {

    List<MinProductAjaxDto> findAllProductOfCategory(Long id);

}
