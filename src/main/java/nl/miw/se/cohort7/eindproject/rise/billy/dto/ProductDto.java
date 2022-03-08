package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Product object for the front-end
 */

@Getter @Setter
public class ProductDto {

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
    private CategoryDto categoryDto;

    @NotNull
    private boolean isProductOfAge;

    public String getPriceDisplayString(double price){
        return String.format("\u20ac %.2f", price);
    }

    public
}
