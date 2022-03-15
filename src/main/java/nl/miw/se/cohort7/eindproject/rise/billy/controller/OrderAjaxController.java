package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ReceiptAjaxResponse;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Regulates the interactive parts of the oder in the webapplication
 */
@RestController
@RequestMapping("/o-api")
public class OrderAjaxController {

    private AssortmentService assortmentService;

    @Autowired
    public OrderAjaxController(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    @PostMapping("/addProduct/{id}")
    public ResponseEntity<?> addProductToOrder(@PathVariable("id") Long id) {
        ReceiptAjaxResponse response = new ReceiptAjaxResponse();

        Optional<ProductDto> optionalProduct = assortmentService.findByProductId(id);
        if(optionalProduct.isPresent()){
            BarOrderDto.addProductToOrder(optionalProduct.get());
        } else{
            response.setMessage("Product not found!");
        }

        setReceiptResponse(response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/removeProduct/{id}")
    public ResponseEntity<?> removeProductFromOrder(@PathVariable("id") Long id) {
        ReceiptAjaxResponse response = new ReceiptAjaxResponse();

        Optional<ProductDto> optionalProduct = assortmentService.findByProductId(id);
        if(optionalProduct.isPresent()){
            BarOrderDto.removeProductFromOrder(optionalProduct.get());
        } else{
            response.setMessage("Product not found!");
        }

        setReceiptResponse(response);

        return ResponseEntity.ok(response);
    }

    private void setReceiptResponse(ReceiptAjaxResponse response){
        response.createReceiptList();
        response.setTotalOrderPrice(BarOrderDto.activeOrder.getTotalPriceDisplayString());
    }
}
