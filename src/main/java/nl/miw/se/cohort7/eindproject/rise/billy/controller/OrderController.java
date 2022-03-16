package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderViewDto;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.BarOrderService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;


/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * Interacties met orders
 */

@Controller
@Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
public class OrderController {

    private BarOrderService barOrderService;
    private AssortmentService assortmentService;

    public OrderController(BarOrderService barOrderService, AssortmentService assortmentService) {
        this.barOrderService = barOrderService;
        this.assortmentService = assortmentService;
    }

    @GetMapping({"/", "/orders/new"})
    protected String setupOrder(Model model) {
        if (BarOrderDto.activeOrder == null){
            BarOrderDto.openNewActiveOrder();
        }
        model.addAttribute("barOrder", BarOrderDto.activeOrder);
        model.addAttribute("allCategories", assortmentService.findAllCategories());
        return "orderForm";
    }

    @GetMapping("/orderHistory")
    @Secured({"ROLE_BAR MANAGER"})
    protected String showOrderHistory(Model model) {
        List<BarOrderViewDto> barOrderViewDtoList = barOrderService.findAll();
        Collections.sort(barOrderViewDtoList);
        model.addAttribute("allOrders", barOrderViewDtoList);
        return "completeOrderHistory";
    }
}
