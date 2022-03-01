package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * This is an Order, it exist out multiple products.
 */

@Entity
@Getter @Setter
public class BarOrder {

    @Id
    @GeneratedValue
    private Long orderId;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private Long bartenderId;
    private String bartenderName;

    @NotNull
    private Long customerId;
    private String customerName;

    @NotNull
    private double totalPrice;

    private String productList; //json
}

