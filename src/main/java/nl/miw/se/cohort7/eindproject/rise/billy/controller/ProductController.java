package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.service.ProductService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 27/01/2022
 * Manage all product-related interactions.
 */

@Controller
@Secured("ROLE_BAR MANAGER")
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    protected String showProductOverview(Model model) {
        model.addAttribute("allProducts", productService.findAll());
        return "productOverview";
    }

    @GetMapping("/new")
    protected String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "productForm";
    }

    @PostMapping("/new")
    protected String saveOrUpdateProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "productForm";
        }
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/update/{productId}")
    protected String showProductForm(@PathVariable("productId") Long productId, Model model) {
        Optional<Product> product = productService.findByProductId(productId);
        model.addAttribute("product", product.get());
        return "productForm";
    }

    @GetMapping("/details/{productId}")
    protected String showProductDetails(@PathVariable("productId") Long productId, Model model) {
        Optional<Product> product = productService.findByProductId(productId);
        model.addAttribute("product", product.get());
        return "productDetails";
    }

    @GetMapping("/delete/{productId}")
    protected String deleteProduct(@PathVariable("productId") Long productId) {
        Optional<Product> product = productService.findByProductId(productId);
        productService.delete(product.get());
        return "redirect:/products";
    }
}
