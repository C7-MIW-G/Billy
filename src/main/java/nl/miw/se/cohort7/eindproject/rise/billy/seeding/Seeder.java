package nl.miw.se.cohort7.eindproject.rise.billy.seeding;

import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BillyUserDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.CategoryDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductDto;
import nl.miw.se.cohort7.eindproject.rise.billy.service.AssortmentService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.BarOrderService;
import nl.miw.se.cohort7.eindproject.rise.billy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
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
    private BarOrderService barOrderService;
    private Date currentDate;

    @Autowired
    public Seeder(AssortmentService assortmentService, UserService userService, BarOrderService barOrderService) {
        this.assortmentService = assortmentService;
        this.userService = userService;
        this.barOrderService = barOrderService;
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

        if (barOrderService.findAll().isEmpty()) {
            seedBarOrders();
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

        BillyUserDto customer7 = new BillyUserDto();
        customer7.setBirthdate(Date.from(Instant.from(LocalDate.of(1972, Month.JANUARY,13))));
        customer7.setAccountBalance(0);
        customer7.setMaxCredit(-50);

        customer7.setName("Johanna Waterman-Van der Heijden");
        customer7.setUsername("customerjohanna@billy.com");
        customer7.setPassword(DEFAULT_PASSWORD);
        customer7.setUserRole("ROLE_CUSTOMER");
        userService.saveNewUser(customer7);
    }

    private void seedCategory(){
        CategoryDto category = new CategoryDto();

        category.setCategoryName("Bier");
        assortmentService.saveCategory(category);

        category.setCategoryName("Wijn");
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

        Optional<CategoryDto> optionalBier = assortmentService.findCategoryByName("Bier").stream().findFirst();
        optionalBier.ifPresent(this::seedBier);

        Optional<CategoryDto> optionalWijn = assortmentService.findCategoryByName("Wijn").stream().findFirst();
        optionalWijn.ifPresent(this::seedWijn);

        Optional<CategoryDto> optionalFrisdrank = assortmentService.findCategoryByName("Frisdrank").stream().findFirst();
        optionalFrisdrank.ifPresent(this::seedFrisdrank);

        Optional<CategoryDto> optionalWarmeDranken = assortmentService.findCategoryByName("Warme dranken").stream().findFirst();
        optionalWarmeDranken.ifPresent(this::seedWarmeDranken);

        Optional<CategoryDto> optionalSnoep = assortmentService.findCategoryByName("Snoep").stream().findFirst();
        optionalSnoep.ifPresent(this::seedSnoep);

        Optional<CategoryDto> optionalPatat = assortmentService.findCategoryByName("Patat").stream().findFirst();
        optionalPatat.ifPresent(this::seedPatat);

        Optional<CategoryDto> optionalSnacks = assortmentService.findCategoryByName("Snacks").stream().findFirst();
        optionalSnacks.ifPresent(this::seedSnacks);
    }

    private void seedBier(CategoryDto bier) {
        ProductDto defaultBier = new ProductDto();
        defaultBier.setCategoryDto(bier);
        defaultBier.setProductOfAge(true);

        defaultBier.setProductName("Heineken 0.3l");
        defaultBier.setProductPrice(2.50);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Heineken 0.5l");
        defaultBier.setProductPrice(4.50);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Meter bier (10 glazen)");
        defaultBier.setProductPrice(22.50);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Heineken 0.0%");
        defaultBier.setProductPrice(2.25);
        defaultBier.setProductOfAge(false);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Amstel Radler");
        defaultBier.setProductPrice(2.50);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Amstel Radler 0.0%");
        defaultBier.setProductPrice(2.25);
        defaultBier.setProductOfAge(false);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Leffe Blond");
        defaultBier.setProductPrice(4.50);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Leffe Blond 0.0%");
        defaultBier.setProductPrice(3.50);
        defaultBier.setProductOfAge(false);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Leffe Bruin");
        defaultBier.setProductPrice(2.50);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Leffe Tripel");
        defaultBier.setProductPrice(4.50);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Franziskaner Weissbier");
        defaultBier.setProductPrice(4.50);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Franziskaner Weissbier 0.0%");
        defaultBier.setProductPrice(3.50);
        defaultBier.setProductOfAge(false);
        assortmentService.saveProduct(defaultBier);
    }

    private void seedWijn(CategoryDto wijn) {
        ProductDto defaultWijn = new ProductDto();
        defaultWijn.setCategoryDto(wijn);
        defaultWijn.setProductOfAge(true);

        defaultWijn.setProductName("Lindemans droge witte wijn");
        defaultWijn.setProductPrice(2.50);
        assortmentService.saveProduct(defaultWijn);

        defaultWijn.setProductName("Hugo zoete witte wijn");
        defaultWijn.setProductPrice(2.50);
        assortmentService.saveProduct(defaultWijn);

        defaultWijn.setProductName("Lindemans Merlot rode wijn");
        defaultWijn.setProductPrice(4.50);
        assortmentService.saveProduct(defaultWijn);

        defaultWijn.setProductName("Lindemans Rosé");
        defaultWijn.setProductPrice(4.50);
        assortmentService.saveProduct(defaultWijn);
    }

    private void seedPatat(CategoryDto patat){
        ProductDto defaultPatat = new ProductDto();
        defaultPatat.setCategoryDto(patat);
        defaultPatat.setProductOfAge(false);

        defaultPatat.setProductName("Patat zonder");
        defaultPatat.setProductPrice(2.00);
        assortmentService.saveProduct(defaultPatat);

        defaultPatat.setProductName("Patat mayonaise");
        defaultPatat.setProductPrice(2.40);
        assortmentService.saveProduct(defaultPatat);

        defaultPatat.setProductName("Patat oorlog");
        defaultPatat.setProductPrice(2.60);
        assortmentService.saveProduct(defaultPatat);

        defaultPatat.setProductName("Patat speciaal");
        defaultPatat.setProductPrice(2.60);
        assortmentService.saveProduct(defaultPatat);
    }

    private void seedSnacks(CategoryDto snacks){
        ProductDto defaultSnack = new ProductDto();
        defaultSnack.setCategoryDto(snacks);
        defaultSnack.setProductOfAge(false);

        defaultSnack.setProductName("Bittergarnituur gemixt 16 stuks");
        defaultSnack.setProductPrice(8);
        assortmentService.saveProduct(defaultSnack);

        defaultSnack.setProductName("Bitterballen 8 stuks met saus");
        defaultSnack.setProductPrice(5);
        assortmentService.saveProduct(defaultSnack);

        defaultSnack.setProductName("Kaasstengels 8 stuks met saus");
        defaultSnack.setProductPrice(5);
        assortmentService.saveProduct(defaultSnack);

        defaultSnack.setProductName("Vlees kroket");
        defaultSnack.setProductPrice(1.75);
        assortmentService.saveProduct(defaultSnack);

        defaultSnack.setProductName("Groente kroket");
        defaultSnack.setProductPrice(1.75);
        assortmentService.saveProduct(defaultSnack);

        defaultSnack.setProductName("Gehaktbal");
        defaultSnack.setProductPrice(2.80);
        assortmentService.saveProduct(defaultSnack);

        defaultSnack.setProductName("Frikandel speciaal");
        defaultSnack.setProductPrice(2.80);
        assortmentService.saveProduct(defaultSnack);

        defaultSnack.setProductName("Frikandel mayonaise");
        defaultSnack.setProductPrice(2.50);
        assortmentService.saveProduct(defaultSnack);

        defaultSnack.setProductName("Kaassouflé");
        defaultSnack.setProductPrice(1.75);
        assortmentService.saveProduct(defaultSnack);

        defaultSnack.setProductName("Broodje");
        defaultSnack.setProductPrice(0.50);
        assortmentService.saveProduct(defaultSnack);
    }

    private void seedFrisdrank(CategoryDto frisdrank){
        ProductDto defaultFrisdrank = new ProductDto();
        defaultFrisdrank.setCategoryDto(frisdrank);
        defaultFrisdrank.setProductOfAge(false);

        defaultFrisdrank.setProductName("Coca Cola");
        defaultFrisdrank.setProductPrice(1.75);
        assortmentService.saveProduct(defaultFrisdrank);

        defaultFrisdrank.setProductName("Fanta");
        defaultFrisdrank.setProductPrice(1.75);
        assortmentService.saveProduct(defaultFrisdrank);

        defaultFrisdrank.setProductName("Ice Tea");
        defaultFrisdrank.setProductPrice(1.75);
        assortmentService.saveProduct(defaultFrisdrank);

        defaultFrisdrank.setProductName("7up");
        defaultFrisdrank.setProductPrice(1.75);
        assortmentService.saveProduct(defaultFrisdrank);

        defaultFrisdrank.setProductName("Cassis");
        defaultFrisdrank.setProductPrice(1.75);
        assortmentService.saveProduct(defaultFrisdrank);

        defaultFrisdrank.setProductName("AA-drink");
        defaultFrisdrank.setProductPrice(2.00);
        assortmentService.saveProduct(defaultFrisdrank);

        defaultFrisdrank.setProductName("Red Bull");
        defaultFrisdrank.setProductPrice(2.00);
        assortmentService.saveProduct(defaultFrisdrank);

        defaultFrisdrank.setProductName("Flesje water");
        defaultFrisdrank.setProductPrice(1.00);
        assortmentService.saveProduct(defaultFrisdrank);
    }

    private void seedWarmeDranken(CategoryDto warmeDranken) {
        ProductDto defaultWarmeDrank = new ProductDto();
        defaultWarmeDrank.setCategoryDto(warmeDranken);
        defaultWarmeDrank.setProductOfAge(false);

        defaultWarmeDrank.setProductName("Koffie");
        defaultWarmeDrank.setProductPrice(2.00);
        assortmentService.saveProduct(defaultWarmeDrank);

        defaultWarmeDrank.setProductName("Thee");
        defaultWarmeDrank.setProductPrice(1.50);
        assortmentService.saveProduct(defaultWarmeDrank);

        defaultWarmeDrank.setProductName("Warme Chocomel met slagroom");
        defaultWarmeDrank.setProductPrice(2.50);
        assortmentService.saveProduct(defaultWarmeDrank);

        defaultWarmeDrank.setProductName("Cappuccino");
        defaultWarmeDrank.setProductPrice(2.00);
        assortmentService.saveProduct(defaultWarmeDrank);

        defaultWarmeDrank.setProductName("Latte Macchiato");
        defaultWarmeDrank.setProductPrice(1.00);
        assortmentService.saveProduct(defaultWarmeDrank);

    }

    private void seedSnoep(CategoryDto snoep) {
        ProductDto defaultSnoep = new ProductDto();
        defaultSnoep.setCategoryDto(snoep);
        defaultSnoep.setProductOfAge(false);

        defaultSnoep.setProductName("Mars");
        defaultSnoep.setProductPrice(1.00);
        assortmentService.saveProduct(defaultSnoep);

        defaultSnoep.setProductName("Snicker");
        defaultSnoep.setProductPrice(1.00);
        assortmentService.saveProduct(defaultSnoep);

        defaultSnoep.setProductName("Twix");
        defaultSnoep.setProductPrice(1.00);
        assortmentService.saveProduct(defaultSnoep);

        defaultSnoep.setProductName("Bounty");
        defaultSnoep.setProductPrice(1.00);
        assortmentService.saveProduct(defaultSnoep);

        defaultSnoep.setProductName("Lion");
        defaultSnoep.setProductPrice(1.00);
        assortmentService.saveProduct(defaultSnoep);

        defaultSnoep.setProductName("Zakje snoep");
        defaultSnoep.setProductPrice(0.50);
        assortmentService.saveProduct(defaultSnoep);
    }

    private void seedBarOrders() {
        BillyUserDto customer1 = new BillyUserDto();
        customer1.setName("Fenna den Hartog");

        BillyUserDto customer2 = new BillyUserDto();
        customer2.setName("Finn den Hartog");

        BillyUserDto customer3 = new BillyUserDto();
        customer3.setName("Rick ten Dam");

        BillyUserDto bartender1 = new BillyUserDto();
        bartender1.setName("Hans de Kraai");

        BillyUserDto bartender2 = new BillyUserDto();
        bartender2.setName("Johan van Dijk");

        BarOrderDto barOrder1 = new BarOrderDto();
        barOrder1.setBartender(bartender1);
        barOrder1.setCustomer(customer1);

        BarOrderDto barOrder2 = new BarOrderDto();
        barOrder2.setBartender(bartender2);
        barOrder2.setCustomer(customer3);
    }


}
