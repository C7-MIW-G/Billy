package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Setter;
import lombok.Getter;
import javax.persistence.*;
import java.util.Objects;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 27/01/2022
 * Describes a product that can be ordered at the bar.
 */

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    private Long productId;

    @Column(nullable = false)
    private String productName;

    private double productPrice;

    @ManyToOne
    private Category category;

    private boolean isProductOfAge;
}


