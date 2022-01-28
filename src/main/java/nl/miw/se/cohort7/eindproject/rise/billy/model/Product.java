package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Setter;
import lombok.Getter;
import org.springframework.lang.Nullable;

<<<<<<< HEAD
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
=======
import javax.lang.model.element.Name;
import javax.persistence.*;
>>>>>>> c985b88946ef89ee1b31a03a8bb379d6c17ad8f0

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
    private long productId;

    private String productName;

    private double productPrice;

    private String productCategory;
}
