package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 27/01/2022
 * Dit is wat het programma doet.
 */

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    private long productId;

    private String productName;

    private double productPrice;

    private String productCategory;

//    @ManyToMany(mappedBy = "productList")
//    private List<BarOrder> orderList = new ArrayList<>();

    public String getDisplayName() {
        return String.format("%20s %4.2f", productName, productPrice);
    }

}
