package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 27/01/2022
 * Manage all product-related interactions.
 */

@Controller
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    protected String showProductOverview(Model model) {
        model.addAttribute("allProducts", productService.findAll());
        return "productOverview";
    }

    @GetMapping("/products/new")
    protected String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "productForm";
    }

    @PostMapping("/products/new")
    protected String saveOrUpdateProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "productForm";
        }
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/update/{productId}")
    protected String showProductForm(@PathVariable("productId") Long productId, Model model) {
        Optional<Product> product = productService.findByProductId(productId);
        model.addAttribute("product", product.get());
        return "productForm";
    }

    @GetMapping("/products/details/{productId}")
    protected String showProductDetails(@PathVariable("productId") Long productId, Model model) {
        Optional<Product> product = productService.findByProductId(productId);
        model.addAttribute("product", product.get());
        return "productDetails";
    }

    @GetMapping("/products/delete/{productId}")
    protected String deleteProduct(@PathVariable("productId") Long productId) {
        Optional<Product> product = productService.findByProductId(productId);
        productService.delete(product.get());
        return "redirect:/products";
    }
}
