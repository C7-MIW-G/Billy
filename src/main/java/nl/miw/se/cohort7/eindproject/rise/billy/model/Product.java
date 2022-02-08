package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Setter;
import lombok.Getter;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 27/01/2022
 * Describes a product that can be ordered at the bar.
 */

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    private long productId;

    @NotNull
    private String productName;

    @NotNull
    private double productPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    public String getPriceDisplayString(double price){
        return String.format("\u20ac %.2f", price);
    }

}


