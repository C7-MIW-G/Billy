package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Setter;
import lombok.Getter;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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


    public static final int MIN_PRODUCT_NAME_LENGTH = 1;
    public static final String MIN_PRODUCT_NAME_ERROR_MESSAGE = "The name of the product should be at least 1 characters";

    public static final int MAX_PRODUCT_NAME_LENGTH = 64;
    public static final String MAX_PRODUCT_NAME_ERROR_MESSAGE = "The name of the product should be less than 65 characters";

    @Id
    @GeneratedValue
    private long productId;

    @Size(min = MIN_PRODUCT_NAME_LENGTH, message = MIN_PRODUCT_NAME_ERROR_MESSAGE)
    @Size(max = MAX_PRODUCT_NAME_LENGTH, message = MAX_PRODUCT_NAME_ERROR_MESSAGE)
    @Column(nullable = false)
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


