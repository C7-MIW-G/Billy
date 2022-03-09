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

    @Id
    @GeneratedValue
    private Long productId;

    @Size(min = 1, message = "The name of the product should be at least 1 character")
    @Size(max = 64, message = "The name of the product should be less than 65 characters")
    @Column(nullable = false)
    private String productName;

    @NotNull
    @Min(value = 0, message = "Please enter a positive number")
    @Max(value = Long.MAX_VALUE, message = "Please try a smaller number.")
    private double productPrice;

    @ManyToOne
    private Category category;

    @NotNull
    private boolean isProductOfAge;

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
}


