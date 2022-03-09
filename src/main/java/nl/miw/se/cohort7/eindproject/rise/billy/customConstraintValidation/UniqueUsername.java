package nl.miw.se.cohort7.eindproject.rise.billy.customConstraintValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 08/03/2022
 * Dit is wat het programma doet.
 */

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "A user with this email address is already registered";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
