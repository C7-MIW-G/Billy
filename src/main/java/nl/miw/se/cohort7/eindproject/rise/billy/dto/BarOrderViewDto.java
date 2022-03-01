package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Object of an existing Order
 */

@Getter
@Setter
public class BarOrderViewDto {

    private Long orderId;

    private LocalDateTime dateTime;

    private Long bartenderId;
    private String bartenderName;

    private Long customerId;
    private String customerName;

    private double totalPrice;

    private List<ProductViewDto> productList = new ArrayList<>();
}
