package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Product object for the front-end
 */

@Getter @Setter
public class ProductDto {

    private Long productId;

    @Size(min = 1, message = "The name of the product should be at least 1 character")
    @Size(max = 64, message = "The name of the product should be less than 65 characters")
    private String productName;

    @NotNull
    @Min(value = 0, message = "Please enter a positive number")
    @Max(value = Long.MAX_VALUE, message = "Please try a smaller number.")
    private double productPrice;

    private CategoryDto categoryDto;

    @NotNull
    private boolean isProductOfAge;

    public String getPriceDisplayString() {
        return String.format("%.2f", productPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
