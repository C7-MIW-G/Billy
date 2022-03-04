package nl.miw.se.cohort7.eindproject.rise.billy.service;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderViewDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BarOrder;

import java.util.List;
import java.util.Optional;

public interface BarOrderService {

    List<BarOrderViewDto> findAllBarOrderOfUser(Long id);

    Optional<BarOrderViewDto> findBarOrderById(Long id);

    void saveBarOrder(BarOrderDto barOrderDto);
}
