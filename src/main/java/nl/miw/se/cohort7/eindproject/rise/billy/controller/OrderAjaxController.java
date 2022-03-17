package nl.miw.se.cohort7.eindproject.rise.billy.controller;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.*;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUserPrincipal;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.BarOrderService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Regulates the interactive parts of the oder in the webapplication
 */
@RestController
@RequestMapping("/o-api")
public class OrderAjaxController {

    private AssortmentService assortmentService;
    private UserService userService;
    private BarOrderService barOrderService;

    @Autowired
    public OrderAjaxController(AssortmentService assortmentService, UserService userService,
                               BarOrderService barOrderService) {
        this.assortmentService = assortmentService;
        this.userService = userService;
        this.barOrderService = barOrderService;
    }

    @GetMapping("/addProduct/{id}/")
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

    @GetMapping("/removeProduct/{id}/")
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

    @GetMapping("/getProducts")
    public ResponseEntity<?> getProductFromOrder(){
        ReceiptAjaxResponse response = new ReceiptAjaxResponse();

        setReceiptResponse(response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/clearOrder")
    public ResponseEntity<?> clearOrder(){
        ReceiptAjaxResponse response = new ReceiptAjaxResponse();

        BarOrderDto.clearActiveOrder();

        setReceiptResponse(response);

        return ResponseEntity.ok(response);
    }

    private void setReceiptResponse(ReceiptAjaxResponse response){
        response.createReceiptList();
        response.setTotalOrderPrice(BarOrderDto.activeOrder.getTotalPriceDisplayString());
    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getAllUserForOrder(){
        UserListAjaxResponse response = new UserListAjaxResponse();

        response.setUserList(userService.getAllForOrder());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/accountPay/{userId}/")
    protected ResponseEntity<?>  doAccountPay(@PathVariable("userId") Long userId) {
        BarOrderDto.activeOrder.setDateTime(LocalDateTime.now());

        // can make payment?
        Optional<OrderUserDto> optionalCustomer = userService.getOneForOrder(userId);
        if(optionalCustomer.isEmpty() || !optionalCustomer.get().isCanBuy()){
            return ResponseEntity.badRequest().body("user not found");
        }
        BillyUserDto customer = userService.findByUserId(optionalCustomer.get().getUserId());

        // update administration
        BillyUserPrincipal principal =
                (BillyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BillyUserDto bartender = userService.findByUserId(principal.getUserId());
        BarOrderDto.activeOrder.setBartender(bartender);
        BarOrderDto.activeOrder.setCustomer(customer);
        barOrderService.saveBarOrder(BarOrderDto.activeOrder);

        customer.payOrder(BarOrderDto.activeOrder.calculateTotalOrderPrice());
        userService.updateUser(customer);

        // open new order
        BarOrderDto.clearActiveOrder();

        return ResponseEntity.ok("");
    }
}
