package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderViewDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BarOrderService {

    List<BarOrderViewDto> findAllBarOrderOfUser(Long id);
}
