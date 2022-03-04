package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Product object with minimal attributes for AJAX
 */
@Getter @Setter
public class MinProductAjaxDto {
    private long productId;
    private String productName;
}
