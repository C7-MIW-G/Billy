package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.model.BarOrder;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.service.ProductService;
import org.springframework.security.access.annotation.Secured;
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

    private ProductService productService;

    public OrderController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/", "/orders/new"})
    protected String setupOrder(Model model) {
        if (BarOrder.activeOrder == null){
            BarOrder.openNewActiveOrder();
            BarOrder.activeOrder.setDateTime(LocalDateTime.now());
        }
        model.addAttribute("barOrder", BarOrder.activeOrder);
        model.addAttribute("allProducts", productService.findAll());
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
}
