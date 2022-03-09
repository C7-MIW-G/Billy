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
                seedUsers();
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
        BillyUserDto managerUser = new BillyUserDto();

        managerUser.setBirthdate(currentDate);
        managerUser.setAccountBalance(0);
        managerUser.setMaxCredit(0);

        managerUser.setName("Bar Manager");
        managerUser.setUsername("billy1@rise.nl");
        managerUser.setPassword(DEFAULT_PASSWORD);
        managerUser.setUserRole("ROLE_BAR MANAGER");
        userService.saveNewUser(managerUser);
    }

    private void seedUsers(){
        BillyUserDto defaultUser = new BillyUserDto();
        defaultUser.setBirthdate(currentDate);
        defaultUser.setAccountBalance(0);
        defaultUser.setMaxCredit(0);

        defaultUser.setName("Bartender");
        defaultUser.setUsername("billy2@rise.nl");
        defaultUser.setPassword(DEFAULT_PASSWORD);
        defaultUser.setUserRole("ROLE_BARTENDER");
        userService.saveNewUser(defaultUser);

        defaultUser.setName("Customer");
        defaultUser.setUsername("billy3@rise.nl");
        defaultUser.setPassword(DEFAULT_PASSWORD);
        defaultUser.setUserRole("ROLE_CUSTOMER");
        userService.saveNewUser(defaultUser);
    }

    private void seedCategory(){
        CategoryDto category = new CategoryDto();

        category.setCategoryName("Patat");
        assortmentService.saveCategory(category);

        category.setCategoryName("Snacks");
        assortmentService.saveCategory(category);

        category.setCategoryName("Dranken");
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