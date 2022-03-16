package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Object of an existing Order
 */

@Getter
@Setter
public class BarOrderViewDto implements Comparable <BarOrderViewDto> {

    private Long orderId;

    private LocalDateTime dateTime;

    private Long bartenderId;
    private String bartenderName;

    private Long customerId;
    private String customerName;

    private double totalPrice;

    private List<ProductViewDto> productList = new ArrayList<>();

    public String getTotalPriceDisplayString(double price) {
        return String.format("%.2f", price);
    }

    public String getTotalPriceEuroString(double price) {
        return String.format("€ %.2f", price);
    }

    @Override
    public int compareTo(@NotNull BarOrderViewDto o) {
        if (o.dateTime.isAfter(this.dateTime)) {
            return 1;
        } else if (o.dateTime.isBefore(this.dateTime)) {
            return -1;
        } else {
            return 0;
        }
    }
}

