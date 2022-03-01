package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * This is an Order, it exist out multiple products.
 */

@Getter @Setter
public class BarOrderDto {
    public static final int DEFAULT_ORDER_PRICE = 0;

    public static BarOrderDto activeOrder = null;

    private LocalDateTime dateTime;

    private List<Product> productList = new ArrayList<>();

    private Map<Product, Integer> productMap = new HashMap<>();

    private Map<Product, Double> discountMap = new HashMap<>();


    public double calculateTotalOrderPrice(){
        double totalOrderPrice = DEFAULT_ORDER_PRICE;
        for (Product product : productMap.keySet()) {
            totalOrderPrice += getSubTotal(product);
        }
        return totalOrderPrice;
    }

    public double getSubTotal(Product product) {
        int amount = activeOrder.productMap.get(product);

        return amount * product.getProductPrice();
    }

    public String getPriceDisplayString(double price){
        return String.format("\u20ac %.2f", price);
    }

    public static void openNewActiveOrder(){
        activeOrder = new BarOrderDto();
    }

    public static void addProductToOrder(Product product){
        if (activeOrder.productMap.containsKey(product)) {
            activeOrder.productMap.put(product, activeOrder.productMap.get(product) + 1);
        } else {
            activeOrder.productMap.put(product, 1);
        }
    }

    public static void removeProductFromOrder(Product product) {
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
