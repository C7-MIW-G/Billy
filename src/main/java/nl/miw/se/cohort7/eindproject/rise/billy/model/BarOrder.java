package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Lars van der Schoor <la.van.der.schoor@st.hanze.nl>
 * This is an Order, it contains multiple products.
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

    @Column(length = 2147483646) //max String size -1
    private String productList; //json
}

