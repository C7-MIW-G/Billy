package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

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

    public String getTotalPriceDisplayString(double price){
        return String.format("%.2f", price);
    }

    public String getTotalPriceEuroString(double price) {
        return String.format("€ %.2f", price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarOrderViewDto that = (BarOrderViewDto) o;
        return dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime);
    }
}
