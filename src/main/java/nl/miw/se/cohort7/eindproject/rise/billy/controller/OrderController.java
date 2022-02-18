package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BarOrder;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.service.ProductService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    private ProductService productService;
    private UserService userService;

    public OrderController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping({"/", "/orders/new"})
    protected String setupOrder(Model model) {
        if (BarOrder.activeOrder == null){
            BarOrder.openNewActiveOrder();
            BarOrder.activeOrder.setDateTime(LocalDateTime.now());
        }
        model.addAttribute("barOrder", BarOrder.activeOrder);
        model.addAttribute("allProducts", productService.findAll());
        model.addAttribute("allUsers", userService.findAll());
        model.addAttribute("selectedUser", new BillyUserDto());
        return "orderForm";
    }

    @GetMapping("/orders/add/{productId}")
    protected String addProductToOrder(@PathVariable("productId") Long productId) {
        Optional<Product> optionalProduct = productService.findByProductId(productId);
        if (optionalProduct.isEmpty()) {
            return "redirect:/orders/new";
        }
        BarOrder.addProductToOrder(optionalProduct.get());
        return "redirect:/orders/new";
    }

    @GetMapping("/orders/remove/{productId}")
    protected String removeProductFromOrder(@PathVariable("productId") Long productId) {
        Optional<Product> optionalProduct = productService.findByProductId(productId);
        if (optionalProduct.isEmpty()) {
            return "redirect:/orders/new";
        }
        BarOrder.removeProductFromOrder(optionalProduct.get());
        return "redirect:/orders/new";
    }

    @GetMapping("/orders/directPay")
    protected String doDirectPay() {
        BarOrder.clearActiveOrder();
        return "redirect:/orders/new";
    }

    @PostMapping("/orders/accountPay")
    protected String doAccountPay(@ModelAttribute("selectedUser") BillyUserDto selectedUser, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/orders/new";
        }
        userService.subtractFromBalance(selectedUser.getUserId(), BarOrder.activeOrder.calculateTotalOrderPrice());
        BarOrder.clearActiveOrder();
        return "redirect:/orders/new";
    }
}
