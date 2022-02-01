package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * This is an Order, it exist out multiple products.
 */

@Getter @Setter
public class BarOrder {

    public static final int DEFAULT_ORDER_PRICE = 0;

    private LocalDateTime dateTime;

    private List<Product> productList = new ArrayList<>();

    private List<Product> addProductList = new ArrayList<>();

    public void addProducts(){
        this.productList.addAll(this.addProductList);
    }

    public void clearAddList(){
        this.addProductList.clear();
    }

    public double calculateTotalOrderPrice(){
        double totalOrderPrice = DEFAULT_ORDER_PRICE;
        for (Product product : productList) {
            totalOrderPrice += product.getProductPrice();
        }
        return totalOrderPrice;
    }

    public String getOrderTotalPriceDisplayString(){
        return String.format("\u20ac %.2f", this.calculateTotalOrderPrice());
    }
}

