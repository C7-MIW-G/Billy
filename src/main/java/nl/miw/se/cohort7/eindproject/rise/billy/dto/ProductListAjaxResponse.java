package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Response Object for Ajax featuring a List of ProductDto's and a message.
 */

@Getter @Setter
public class ProductListAjaxResponse {

    private List<ProductDto> productList;

    private String message;
}
