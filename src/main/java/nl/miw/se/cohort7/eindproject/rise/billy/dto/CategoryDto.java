package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.validation.constraints.Size;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Category object for the front-end
 */

@Getter @Setter
public class CategoryDto implements Comparable<CategoryDto> {

    private long categoryId;

    @Size(min = 1, message = "The name of the category should be at least 1 character")
    @Size(max = 32, message = "The name of the category should be less than 33 characters")
    @Column(nullable = false)
    private String categoryName;

    @Override
    public int compareTo(@NotNull CategoryDto o) {
        return this.categoryName.toLowerCase().compareTo(o.getCategoryName().toLowerCase());
    }
}
