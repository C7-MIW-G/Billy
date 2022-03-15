package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Product with neccesary information for receipt
 */
@Getter @Setter
public class ReceiptProductDto {

    private ProductDto product;

    private Integer amount;

    private String priceDisplay;
}
