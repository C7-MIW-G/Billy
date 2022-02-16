package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 15/02/2022
 * Dit is wat het programma doet.
 */

@Getter @Setter
public class ChangePassword {
    private Long userId;

    @Size(min = 8, message = "Password should be min 8 characters long")
    private String oldPassword;

    @Size(min = 8, message = "Password should be min 8 characters long")
    private String newPassword;

    private String newPasswordConfirmation;

    public boolean confirmNewPassword() {
        return newPassword.equals(newPasswordConfirmation);
    }
}
