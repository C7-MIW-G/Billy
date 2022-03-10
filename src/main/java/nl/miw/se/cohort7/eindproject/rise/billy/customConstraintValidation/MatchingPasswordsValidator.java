package nl.miw.se.cohort7.eindproject.rise.billy.customConstraintValidation;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.PasswordDto;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 10/03/2022
 * Dit is wat het programma doet.
 */

@Component
public class MatchingPasswordsValidator implements ConstraintValidator<MatchingPasswords, PasswordDto> {
    @Override
    public boolean isValid(PasswordDto passwordDto, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = passwordDto.getNewPassword().equals(passwordDto.getNewPasswordConfirmation());

        if(!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("newPasswordConfirmation").addConstraintViolation();
        }

        return isValid;
    }
}
