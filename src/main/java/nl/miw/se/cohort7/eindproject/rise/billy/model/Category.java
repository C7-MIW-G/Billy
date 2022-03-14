package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
