package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductListAjaxResponse;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Regulates the interactive parts of pages in the webapplication
 */

@RestController
@RequestMapping("/a-api")
public class AssortmentAjaxController {

    private AssortmentService assortmentService;

    @Autowired
    public AssortmentAjaxController(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }


    @PostMapping("/getProducts/{id}")
    public ResponseEntity<?> getProductsFromCategory(@PathVariable("id") Long id) {
        ProductListAjaxResponse response = new ProductListAjaxResponse();

        List<ProductDto> productList = assortmentService.findAllProductOfCategory(id);

        response.setProductList(productList);

        return ResponseEntity.ok(response);
    }

}
