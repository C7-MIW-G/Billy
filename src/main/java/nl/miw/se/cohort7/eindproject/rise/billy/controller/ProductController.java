package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.ProductRepository;
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

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping({"/","/products"})
    protected String showProductOverview(Model model) {
        model.addAttribute("allProducts", productRepository.findAll());
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
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/update/{productName}")
    protected String showProductForm(@PathVariable("productName") String productName, Model model) {
        Optional<Product> product = productRepository.findByProductName(productName);
        if (productName.isEmpty()) {
            return "redirect:/products";
        }
        model.addAttribute("product", product.get());
        return "productForm";
    }

    @GetMapping("/products/details/{productName}")
    protected String showProductDetails(@PathVariable("productName") String productName, Model model) {
        Optional<Product> product = productRepository.findByProductName(productName);
        if (productName.isEmpty()) {
            return "redirect:/products";
        }
        model.addAttribute("product", product.get());
        return "productDetails";

    }

    @GetMapping("/products/delete/{productName}")
    protected String deleteProduct(@PathVariable("productName") String productName) {
        Optional<Product> product = productRepository.findByProductName(productName);
        if (productName.isEmpty()) {
            return "redirect:/products";
        }
        productRepository.delete(product.get());
        return "redirect:/products";
    }



}
