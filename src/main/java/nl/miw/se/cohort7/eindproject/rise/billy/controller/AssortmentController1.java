package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Manage category and product related interactions
 */

@Controller
//@Secured("ROLE_BAR MANAGER")
@RequestMapping("/assortment1")
public class AssortmentController1 {

    private AssortmentService assortmentService;

    public AssortmentController1(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    @GetMapping({"", "/"})
    protected String showCategoryOverview(Model model) {
        model.addAttribute("allCategories", assortmentService.findAllCategories());
        List<ProductDto> products = new ArrayList<>();
        model.addAttribute("categoryProducts", products);
        return "combinedAssortmentOverview";
    }
}
