package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * User model for use in Orderscreen.
 */
@Getter @Setter
public class OrderUserDto implements Comparable<OrderUserDto>{

    private Long userId;
    private String displayName;
    private double accountBalance;

    private boolean canBuy;

    private List<String> restrictions = new ArrayList<>();


    public void userCanBuy(double maxCredit, Date dateOfBirth){
        boolean balance = canPayForOrder(maxCredit);
        boolean ofAge = isOfAgeForOrder(dateOfBirth);
        setCanBuy(balance && ofAge);
    }

    private boolean canPayForOrder(double maxCredit) {
        if(maxCredit > (this.accountBalance - BarOrderDto.activeOrder.calculateTotalOrderPrice())){
            this.restrictions.add("fab fa-creative-commons-nc-eu"); //no money icon
            return false;
        }
        return true;
    }

    private boolean isOfAgeForOrder(Date dateOfBirth){
        for (ProductDto product : BarOrderDto.activeOrder.getProductMap().keySet()) {
            if (product.isProductOfAge() && !isUserEighteenPlus(dateOfBirth)) {
                this.restrictions.add("fas fa-wine-bottle"); //not drinking age
                return false;
            }
        }
        return true;
    }

    private int getAge(Date dateOfBirth) {
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        return today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
    }

    private boolean isUserEighteenPlus(Date dateOfBirth) {
        return getAge(dateOfBirth) >= BarOrderDto.MIN_AGE_FOR_PRODUCTS_OF_AGE;
    }

    @Override
    public int compareTo(@org.jetbrains.annotations.NotNull OrderUserDto o) {
        return this.getDisplayName().toLowerCase().compareTo(o.getDisplayName().toLowerCase());
    }
}
