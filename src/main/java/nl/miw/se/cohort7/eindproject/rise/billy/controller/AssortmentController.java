package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Manage category and product related interactions
 */

@Controller
@RequestMapping("/assortment")
public class AssortmentController {

    private AssortmentService assortmentService;

    public AssortmentController(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    @GetMapping("")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String showUserOverview(Model model) {
        model.addAttribute("allCategories", assortmentService.findAllCategories());
        return "assortmentOverview";
    }

    @GetMapping("/new")
    @Secured({"ROLE_BARTENDER", "ROLE_BAR MANAGER"})
    protected String showProductForm(Model model) {
        model.addAttribute("newCategory", new CategoryDto());
        model.addAttribute("headerText", "New Category");
        return "assortmentForm";
    }

    @PostMapping("/new")
    protected String saveOrUpdateProduct(@Valid @ModelAttribute("newCategory") CategoryDto category,
                                         BindingResult result) {
        if (result.hasErrors()) {
            return "assortmentForm";
        }
        assortmentService.saveCategory(category);
        return "redirect:/assortment";
    }
}
