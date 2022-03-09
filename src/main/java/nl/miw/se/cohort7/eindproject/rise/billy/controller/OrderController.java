package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUserPrincipal;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.service.BarOrderService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.ProductService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
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
    private ProductService productService;
    private UserService userService;

    public OrderController(BarOrderService barOrderService, ProductService productService, UserService userService) {
        this.barOrderService = barOrderService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping({"/", "/orders/new"})
    protected String setupOrder(Model model) {
        if (BarOrderDto.activeOrder == null){
            BarOrderDto.openNewActiveOrder();
            BarOrderDto.activeOrder.setDateTime(LocalDateTime.now());
        }
        model.addAttribute("barOrder", BarOrderDto.activeOrder);
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("order", barOrderService.findAll());
        model.addAttribute("selectedUser", new BillyUserDto());
        return "orderForm";
    }

    @GetMapping("/orders/add/{productId}")
    protected String addProductToOrder(@PathVariable("productId") Long productId) {
        Optional<Product> optionalProduct = productService.findByProductId(productId);
        if (optionalProduct.isEmpty()) {
            return "redirect:/orders/new";
        }
        BarOrderDto.addProductToOrder(optionalProduct.get());
        return "redirect:/orders/new";
    }

    @GetMapping("/orders/remove/{productId}")
    protected String removeProductFromOrder(@PathVariable("productId") Long productId) {
        Optional<Product> optionalProduct = productService.findByProductId(productId);
        if (optionalProduct.isEmpty()) {
            return "redirect:/orders/new";
        }
        BarOrderDto.removeProductFromOrder(optionalProduct.get());
        return "redirect:/orders/new";
    }

    @GetMapping("/orders/directPay")
    protected String doDirectPay() {
        BarOrderDto.clearActiveOrder();
        return "redirect:/orders/new";
    }

    @GetMapping("/orders/accountPay/{userId}")
    protected String doAccountPay(@PathVariable("userId") Long userId) {
        BillyUserDto customer = userService.findByUserId(userId);
        if (!userService.hasEnoughBalance(customer)) {
            return "redirect:/orders/new";
        }
        userService.subtractFromBalance(userId, BarOrderDto.activeOrder.calculateTotalOrderPrice());
        BillyUserPrincipal principal =
                (BillyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BillyUserDto bartender = userService.findByUserId(principal.getUserId());
        BarOrderDto.activeOrder.setBartender(bartender);
        BarOrderDto.activeOrder.setCustomer(customer);
        barOrderService.saveBarOrder(BarOrderDto.activeOrder);
        BarOrderDto.clearActiveOrder();
        return "redirect:/orders/new";
    }

    @GetMapping("/orderHistory")
    protected String showOrderHistory(Model model) {
        model.addAttribute("allOrders", barOrderService.findAll());
        return "completeOrderHistory";
    }
}
