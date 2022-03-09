package nl.miw.se.cohort7.eindproject.rise.billy.customConstraintValidation;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
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
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, BillyUserDto> {

    UserService userService;

    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(BillyUserDto billyUserDto, ConstraintValidatorContext context) {
        boolean isValid = userService.isUsernameUnique(billyUserDto);

        if(!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("username").addConstraintViolation();
        }

        return isValid;
    }
}
