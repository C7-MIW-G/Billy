package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Show relevant bought product details
 */
@Getter
@Setter
public class ProductViewDto {

    private int amount;

    private Long productId;
    private String productName;
    private double productPrice;

    private double discountPrice;

    public double subTotal() {
        return amount * productPrice;
    }

    public String subTotalDisplayString() {
        return String.format("\u20ac %.2f", subTotal());
    }
}
