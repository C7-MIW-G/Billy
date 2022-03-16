package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * This is an Order, it contains multiple products.
 */

@Getter @Setter
public class BarOrderDto {
    public static final int DEFAULT_ORDER_PRICE = 0;
    public static final int MIN_AGE_FOR_PRODUCTS_OF_AGE = 18;

    public static BarOrderDto activeOrder = null;

    private LocalDateTime dateTime;
    
    private BillyUserDto bartender;
    
    private BillyUserDto customer;

    private Map<ProductDto, Integer> productMap = new HashMap<>();

    private Map<ProductDto, Double> discountMap = new HashMap<>();


    public double calculateTotalOrderPrice(){
        double totalOrderPrice = DEFAULT_ORDER_PRICE;
        for (ProductDto product : productMap.keySet()) {
            totalOrderPrice += getSubTotal(product);
        }
        return totalOrderPrice;
    }

    public double getSubTotal(ProductDto product) {
        int amount = activeOrder.productMap.get(product);

        return amount * product.getProductPrice();
    }

    public String getSubTotalDisplayString(ProductDto product){
        double subTotal = getSubTotal(product);
        return String.format("%.2f", subTotal);
    }

    public String getTotalPriceDisplayString() {
        return String.format("%.2f", calculateTotalOrderPrice());
    }

    public String getUserCreditDisplayString() {
        return String.format("%.2f", customer.getAccountBalance());
    }

    public static void openNewActiveOrder(){
        activeOrder = new BarOrderDto();
    }

    public static void addProductToOrder(ProductDto product){
        if (activeOrder.productMap.containsKey(product)) {
            activeOrder.productMap.put(product, activeOrder.productMap.get(product) + 1);
        } else {
            activeOrder.productMap.put(product, 1);
        }
    }

    public static void removeProductFromOrder(ProductDto product) {
        if (activeOrder.productMap.get(product) == 1) {
            activeOrder.productMap.remove(product);
        } else {
            activeOrder.productMap.put(product, activeOrder.productMap.get(product) - 1);
        }
    }

    public static void clearActiveOrder() {
        activeOrder = null;
    }

}
