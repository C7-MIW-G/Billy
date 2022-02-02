package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.model.BarOrder;
import nl.miw.se.cohort7.eindproject.rise.billy.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * <p>
 * Interacties met orders
 */

@Controller
public class OrderController {

    private ProductService productService;

    public OrderController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/", "/orders/new"})
    protected String setupOrder(Model model) {
        BarOrder barOrder = new BarOrder();
        barOrder.setDateTime(LocalDateTime.now());
        model.addAttribute("barOrder", barOrder);
        model.addAttribute("allProducts", productService.findAll());
        return "orderForm";
    }

    @PostMapping({"/orders/new"})
    protected String addToOrder(@ModelAttribute("barOrder") BarOrder barOrder,
                                BindingResult result, Model model){
        if(!result.hasErrors()){
            barOrder.addProducts();
            barOrder.clearAddList();
            model.addAttribute("barOrder", barOrder);
            model.addAttribute("allProducts", productService.findAll());
            return "orderForm";
        }
        return "redirect:/";
    }
}
