package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Describes a category of a product
 */

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    private long categoryId;

    @Size(min = 1, message = "The name of the category is not at least 1 character")
    @Size(max = 32, message = "The name of the category is not less than 33 characters")
    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
