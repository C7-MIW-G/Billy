package nl.miw.se.cohort7.eindproject.rise.billy.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 15/02/2022
 * Dit is wat het programma doet.
 */

@Getter @Setter
public class ChangePassword {
    private Long userId;

    private String oldPassword;

    private String newPassword;

    private String newPasswordConfirmation;

    public boolean confirmNewPassword() {
        return newPassword.equals(newPasswordConfirmation);
    }
}
