package nl.miw.se.cohort7.eindproject.rise.billy.service.DtoConverter;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;

/**
 * @author Martijn Gäbler <m.gabler@st.hanze.nl>
 * Date created: 08/03/2022
 * Dit is wat het programma doet.
 */

public class BillyUserDtoConverter {

    public BillyUser toBillyUser(BillyUserDto billyUserDto) {
        BillyUser billyUser = new BillyUser();


        billyUser.setUserId(billyUserDto.getUserId() == null ? 0 : billyUserDto.getUserId());

        billyUser.setUserRole(billyUserDto.getUserRole());
        billyUser.setName(billyUserDto.getName());
        billyUser.setPassword(billyUserDto.getPassword());
        billyUser.setUsername(billyUserDto.getUsername());
        billyUser.setMaxCredit(billyUserDto.getMaxCredit());
        billyUser.setBirthdate(billyUserDto.getBirthdate());
        billyUser.setAccountBalance(billyUserDto.getAccountBalance());

        return billyUser;
    }

    public BillyUserDto convertToDto(BillyUser billyUser) {
        BillyUserDto billyUserDto = new BillyUserDto();

        billyUserDto.setUserId(billyUser.getUserId());
        billyUserDto.setUserRole(billyUser.getUserRole());
        billyUserDto.setName(billyUser.getName());
        billyUserDto.setUsername(billyUser.getUsername());
        billyUserDto.setMaxCredit(billyUser.getMaxCredit());
        billyUserDto.setBirthdate(billyUser.getBirthdate());
        billyUserDto.setAccountBalance(billyUser.getAccountBalance());

        return billyUserDto;
    }
}
