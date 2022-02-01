package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 27/01/2022
 * Describes a product that can be ordered at the bar.
 */

@Entity
@Getter
@Setter
public class Product {

    public static final int MIN_CHAR_PRODUCT_NAME = 1;
    public static final String MESSAGE_MIN_PRODUCT_NAME = "The name of the product must be at least 1 character";

    public static final int MAX_CHAR_PRODUCT_NAME = 64;
    public static final String MESSAGE_MAX_PRODUCT_NAME = "The name of the product should be less than 65 characters";

    public static final int MIN_VALUE_PRODUCT_PRICE = 0;
    public static final String MESSAGE_MIN_PRODUCT_PRICE = "The price of the product may not be negative";


    @Id
    @GeneratedValue
    private long productId;

    @Size(min = MIN_CHAR_PRODUCT_NAME, message = MESSAGE_MIN_PRODUCT_NAME)
    @Size(max = MAX_CHAR_PRODUCT_NAME, message = MESSAGE_MAX_PRODUCT_NAME)
    private String productName;

    @Min(value = MIN_VALUE_PRODUCT_PRICE, message = MESSAGE_MIN_PRODUCT_PRICE)
    private double productPrice;

    private String productCategory;
}
