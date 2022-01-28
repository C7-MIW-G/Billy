package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * This is an Order, it exist out multiple products.
 */

@Getter @Setter
public class BarOrder {

    private LocalDateTime dateTime;

    private List<Product> productList = new ArrayList<>();

    private List<Product> addProductList = new ArrayList<>();

    public void addProducts(){
        this.productList.addAll(this.addProductList);
    }

    public void clearAddList(){
        this.addProductList.clear();
    }
}
