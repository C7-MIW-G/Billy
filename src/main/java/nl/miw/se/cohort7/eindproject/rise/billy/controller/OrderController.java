package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderViewDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.OrderUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUserPrincipal;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.BarOrderService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * Interacties met orders
 */

@Controller
@Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
public class OrderController {

    private BarOrderService barOrderService;
    private UserService userService;
    private AssortmentService assortmentService;

    public OrderController(BarOrderService barOrderService, UserService userService, AssortmentService assortmentService) {
        this.barOrderService = barOrderService;
        this.userService = userService;
        this.assortmentService = assortmentService;
    }

    @GetMapping({"/", "/orders/new"})
    protected String setupOrder(Model model) {
        if (BarOrderDto.activeOrder == null){
            BarOrderDto.openNewActiveOrder();
        }
        model.addAttribute("barOrder", BarOrderDto.activeOrder);
        model.addAttribute("allCategories", assortmentService.findAllCategories());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("selectedUser", new BillyUserDto());
        return "orderForm";
    }

    @GetMapping("/orders/accountPay/{userId}")
    protected String doAccountPay(@PathVariable("userId") Long userId) {
        BarOrderDto.activeOrder.setDateTime(LocalDateTime.now());

        // can make payment?
        Optional<OrderUserDto> optionalCustomer = userService.getOneForOrder(userId);
        if(optionalCustomer.isEmpty() || !optionalCustomer.get().isCanBuy()){
            return "redirect:/";
        }
        BillyUserDto customer = userService.findByUserId(optionalCustomer.get().getUserId());

        // update administration
        BillyUserPrincipal principal =
                (BillyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BillyUserDto bartender = userService.findByUserId(principal.getUserId());
        BarOrderDto.activeOrder.setBartender(bartender);
        BarOrderDto.activeOrder.setCustomer(customer);
        barOrderService.saveBarOrder(BarOrderDto.activeOrder);

        customer.payOrder(BarOrderDto.activeOrder.calculateTotalOrderPrice());
        userService.updateUser(customer);

        // open new order
        BarOrderDto.clearActiveOrder();
        return "redirect:/";
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
