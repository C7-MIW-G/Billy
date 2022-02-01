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
    protected String saveOrUpdateProduct(@ModelAttribute("product") Product product, BindingResult result) {
        if (result.hasErrors()) {
            return "productForm";
        }
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/update/{productName}")
    protected String showProductForm(@PathVariable("productName") String productName, Model model) {
        Optional<Product> product = productService.findByProductName(productName);
        if (productName.isEmpty()) {
            return "redirect:/products";
        }
        model.addAttribute("product", product.get());
        return "productForm";
    }

    @GetMapping("/products/details/{productName}")
    protected String showProductDetails(@PathVariable("productName") String productName, Model model) {
        Optional<Product> product = productService.findByProductName(productName);
        if (productName.isEmpty()) {
            return "redirect:/products";
        }
        model.addAttribute("product", product.get());
        return "productDetails";

    }

    @GetMapping("/products/delete/{productName}")
    protected String deleteProduct(@PathVariable("productName") String productName) {
        Optional<Product> product = productService.findByProductName(productName);
        if (productName.isEmpty()) {
            return "redirect:/products";
        }
        productService.delete(product.get());
        return "redirect:/products";
    }
}
