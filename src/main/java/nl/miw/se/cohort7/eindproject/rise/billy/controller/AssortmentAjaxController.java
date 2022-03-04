package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.IdSearchDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductListAjaxResponse;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    @PostMapping("/getProducts")
    public ResponseEntity<?> getProductsFromCategory(@Valid @RequestBody IdSearchDto searchData, Errors errors) {
        ProductListAjaxResponse response = new ProductListAjaxResponse();

        if (errors.hasErrors()){
            response.setMessage(errors.getAllErrors()
                    .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(response);
        }

        List<ProductDto> productList = new ArrayList<>();

        if(searchData.getId() != null){
            productList = assortmentService.findAllProductOfCategory(searchData.getId());
        }
        response.setProductList(productList);

        return ResponseEntity.ok(response);
    }

}
