package nl.miw.se.cohort7.eindproject.rise.billy.seeding;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.Optional;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Seeder for billy app. Sets default data to database.
 */
@Component
public class Seeder {

    private final String DEFAULT_PASSWORD = "123456789";

    private AssortmentService assortmentService;
    private UserService userService;
    private Date currentDate;

    @Autowired
    public Seeder(AssortmentService assortmentService, UserService userService) {
        this.assortmentService = assortmentService;
        this.userService = userService;
        this.currentDate = Date.from(Instant.now());
    }

    @EventListener
    public void seed(ContextRefreshedEvent refreshedEvent){
        if (userService.findUsersByRole("ROLE_BAR MANAGER").isEmpty()){
            if (userService.findAll().isEmpty()){
                seedBartenders();
                seedCustomers();
            }
            seedManager();
        }

        if (assortmentService.findAllCategories().isEmpty()
                && assortmentService.findAllProducts().isEmpty()){
            seedCategory();
            seedProducts();
        }
    }

    private void seedManager(){
        BillyUserDto managerUser1 = new BillyUserDto();

        managerUser1.setBirthdate(Date.from(Instant.from(LocalDate.of(1990, Month.APRIL,15))));
        managerUser1.setAccountBalance(0);
        managerUser1.setMaxCredit(-125);

        managerUser1.setName("Erik de Vries");
        managerUser1.setUsername("barmanager@billy.com");
        managerUser1.setPassword(DEFAULT_PASSWORD);
        managerUser1.setUserRole("ROLE_BAR MANAGER");
        userService.saveNewUser(managerUser1);

        BillyUserDto managerUser2 = new BillyUserDto();

        managerUser2.setBirthdate(Date.from(Instant.from(LocalDate.of(1963, Month.JANUARY,5))));
        managerUser2.setAccountBalance(0);
        managerUser2.setMaxCredit(0);

        managerUser2.setName("José de Groot");
        managerUser2.setUsername("barmanagerjose@billy.com");
        managerUser2.setPassword(DEFAULT_PASSWORD);
        managerUser2.setUserRole("ROLE_BAR MANAGER");
        userService.saveNewUser(managerUser2);
    }

    private void seedBartenders(){
        BillyUserDto bartender1 = new BillyUserDto();
        bartender1.setBirthdate(Date.from(Instant.from(LocalDate.of(1980, Month.AUGUST,23))));
        bartender1.setAccountBalance(0);
        bartender1.setMaxCredit(0);

        bartender1.setName("Hans de Kraai");
        bartender1.setUsername("bartenderhans@billy.com");
        bartender1.setPassword(DEFAULT_PASSWORD);
        bartender1.setUserRole("ROLE_BARTENDER");
        userService.saveNewUser(bartender1);

        BillyUserDto bartender2 = new BillyUserDto();
        bartender2.setBirthdate(Date.from(Instant.from(LocalDate.of(1949, Month.DECEMBER,28))));
        bartender2.setAccountBalance(0);
        bartender2.setMaxCredit(-100);

        bartender2.setName("Johan van Dijk");
        bartender2.setUsername("bartenderjohan@billy.com");
        bartender2.setPassword(DEFAULT_PASSWORD);
        bartender2.setUserRole("ROLE_BARTENDER");
        userService.saveNewUser(bartender2);

        BillyUserDto bartender3 = new BillyUserDto();
        bartender3.setBirthdate(Date.from(Instant.from(LocalDate.of(2002, Month.FEBRUARY,2))));
        bartender3.setAccountBalance(0);
        bartender3.setMaxCredit(-50);

        bartender3.setName("Erica van Drie");
        bartender3.setUsername("bartendererica@billy.com");
        bartender3.setPassword(DEFAULT_PASSWORD);
        bartender3.setUserRole("ROLE_BARTENDER");
        userService.saveNewUser(bartender3);
    }

    private void seedCustomers(){
        BillyUserDto customer1 = new BillyUserDto();
        customer1.setBirthdate(Date.from(Instant.from(LocalDate.of(2005, Month.SEPTEMBER,13))));
        customer1.setAccountBalance(0);
        customer1.setMaxCredit(0);

        customer1.setName("Fenna den Hartog");
        customer1.setUsername("customerfenna@billy.com");
        customer1.setPassword(DEFAULT_PASSWORD);
        customer1.setUserRole("ROLE_CUSTOMER");
        userService.saveNewUser(customer1);

        BillyUserDto customer2 = new BillyUserDto();
        customer2.setBirthdate(Date.from(Instant.from(LocalDate.of(2010, Month.OCTOBER,19))));
        customer2.setAccountBalance(0);
        customer2.setMaxCredit(0);

        customer2.setName("Finn den Hartog");
        customer2.setUsername("customerfinn@billy.com");
        customer2.setPassword(DEFAULT_PASSWORD);
        customer2.setUserRole("ROLE_CUSTOMER");
        userService.saveNewUser(customer2);

        BillyUserDto customer3 = new BillyUserDto();
        customer3.setBirthdate(Date.from(Instant.from(LocalDate.of(2004, Month.MAY,13))));
        customer3.setAccountBalance(0);
        customer3.setMaxCredit(0);

        customer3.setName("Rick ten Dam");
        customer3.setUsername("customerrick@billy.com");
        customer3.setPassword(DEFAULT_PASSWORD);
        customer3.setUserRole("ROLE_CUSTOMER");
        userService.saveNewUser(customer3);

        BillyUserDto customer4 = new BillyUserDto();
        customer4.setBirthdate(Date.from(Instant.from(LocalDate.of(2007, Month.NOVEMBER,21))));
        customer4.setAccountBalance(0);
        customer4.setMaxCredit(0);

        customer4.setName("Steven de Kraai");
        customer4.setUsername("customersteven@billy.com");
        customer4.setPassword(DEFAULT_PASSWORD);
        customer4.setUserRole("ROLE_CUSTOMER");
        userService.saveNewUser(customer4);

        BillyUserDto customer5 = new BillyUserDto();
        customer5.setBirthdate(Date.from(Instant.from(LocalDate.of(1975, Month.JULY,17))));
        customer5.setAccountBalance(0);
        customer5.setMaxCredit(-175);

        customer5.setName("Ria de Vries");
        customer5.setUsername("customerria@billy.com");
        customer5.setPassword(DEFAULT_PASSWORD);
        customer5.setUserRole("ROLE_CUSTOMER");
        userService.saveNewUser(customer5);

        BillyUserDto customer6 = new BillyUserDto();
        customer6.setBirthdate(Date.from(Instant.from(LocalDate.of(1967, Month.JUNE,13))));
        customer6.setAccountBalance(0);
        customer6.setMaxCredit(0);

        customer6.setName("Els");
        customer6.setUsername("customerels@billy.com");
        customer6.setPassword(DEFAULT_PASSWORD);
        customer6.setUserRole("ROLE_CUSTOMER");
        userService.saveNewUser(customer6);
    }

    private void seedCategory(){
        CategoryDto category = new CategoryDto();

        category.setCategoryName("Bier");
        assortmentService.saveCategory(category);

        category.setCategoryName("Frisdrank");
        assortmentService.saveCategory(category);

        category.setCategoryName("Warme dranken");
        assortmentService.saveCategory(category);

        category.setCategoryName("Snoep");
        assortmentService.saveCategory(category);

        category.setCategoryName("Patat");
        assortmentService.saveCategory(category);

        category.setCategoryName("Snacks");
        assortmentService.saveCategory(category);
    }

    private void seedProducts(){
        Optional<CategoryDto> optionalPatat = assortmentService.findCategoryByName("Patat").stream().findFirst();
        optionalPatat.ifPresent(this::seedPatat);

        Optional<CategoryDto> optionalSnacks = assortmentService.findCategoryByName("Snacks").stream().findFirst();
        optionalSnacks.ifPresent(this::seedSnacks);

        Optional<CategoryDto> optionalDranken = assortmentService.findCategoryByName("Dranken").stream().findFirst();
        optionalDranken.ifPresent(this::seedDranken);
    }

    private void seedPatat(CategoryDto patat){
        ProductDto defaultPatat = new ProductDto();
        defaultPatat.setCategoryDto(patat);
        defaultPatat.setProductOfAge(false);

        defaultPatat.setProductName("Patat Zonder");
        defaultPatat.setProductPrice(2.00);
        assortmentService.saveProduct(defaultPatat);

        defaultPatat.setProductName("Patat Mayo");
        defaultPatat.setProductPrice(2.40);
        assortmentService.saveProduct(defaultPatat);

        defaultPatat.setProductName("Patat Oorlog");
        defaultPatat.setProductPrice(2.60);
        assortmentService.saveProduct(defaultPatat);
    }

    private void seedSnacks(CategoryDto snacks){
        ProductDto defaultSnack = new ProductDto();
        defaultSnack.setCategoryDto(snacks);
        defaultSnack.setProductOfAge(false);

        defaultSnack.setProductName("Vlees Kroket");
        defaultSnack.setProductPrice(1.75);
        assortmentService.saveProduct(defaultSnack);

        defaultSnack.setProductName("Gehaktbal");
        defaultSnack.setProductPrice(2.80);
        assortmentService.saveProduct(defaultSnack);
    }

    private void seedDranken(CategoryDto dranken){
        ProductDto defaultDrank = new ProductDto();
        defaultDrank.setCategoryDto(dranken);

        defaultDrank.setProductName("Coca Cola");
        defaultDrank.setProductPrice(1.75);
        defaultDrank.setProductOfAge(false);
        assortmentService.saveProduct(defaultDrank);

        defaultDrank.setProductName("Heineken");
        defaultDrank.setProductPrice(1.85);
        defaultDrank.setProductOfAge(true);
        assortmentService.saveProduct(defaultDrank);
    }
}
