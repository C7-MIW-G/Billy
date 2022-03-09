package nl.miw.se.cohort7.eindproject.rise.billy.customConstraintValidation;

import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 08/03/2022
 * Dit is wat het programma doet.
 */

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    UserService userService;

    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return userService.existsUsername(username);
    }
}
