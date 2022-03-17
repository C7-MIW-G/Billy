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

import java.time.*;
import java.util.*;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Seeder for billy app. Sets default data to database.
 */
@Component
public class Seeder {

    private static final int RANDOM_UPPER_LIMIT_NUMBER_OF_PRODUCTS = 10;
    private static final int RANDOM_UPPER_LIMIT_PRODUCT_AMOUNT = 5;
    private static final int RANDOM_UPPER_LIMIT_ORDER_AMOUNT = 17;
    private static final int RANDOM_LOWER_LIMIT = 1;
    private static final String DEFAULT_PASSWORD = "123456789";

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
        if (assortmentService.findAllCategories().isEmpty()
                && assortmentService.findAllProducts().isEmpty()){
            seedCategory();
            seedProducts();
        }

        if (userService.findUsersByRole("ROLE_BAR MANAGER").isEmpty()){
            if (userService.findAll().isEmpty()){
                seedBartenders();
                seedCustomers();
                seedOrder();
            }
            seedManager();
        }
    }

    private BillyUserDto createBasicUser(String name, Date date, double balance, double maxCredit){
        BillyUserDto user = new BillyUserDto();

        user.setName(name);
        user.setPassword(DEFAULT_PASSWORD);
        user.setBirthdate(date);
        user.setAccountBalance(balance);
        user.setMaxCredit(maxCredit);

        return user;
    }

    private void saveAsManager(List<BillyUserDto> managerList){
        for (BillyUserDto manager : managerList) {
            String email = String.format("barmanager%d@billy.com", managerList.lastIndexOf(manager) + 1);
            manager.setUsername(email);
            manager.setUserRole("ROLE_BAR MANAGER");

            userService.saveNewUser(manager);
        }
    }

    private void saveAsBartender(List<BillyUserDto> bartenderList){
        for (BillyUserDto bartender : bartenderList) {
            String email = String.format("bartender%d@billy.com", bartenderList.lastIndexOf(bartender) + 1);
            bartender.setUsername(email);
            bartender.setUserRole("ROLE_BARTENDER");

            userService.saveNewUser(bartender);
        }
    }

    private void saveAsCustomer(List<BillyUserDto> customerList){
        for (BillyUserDto customer : customerList) {
            String email = String.format("customer%d@billy.com", customerList.lastIndexOf(customer) + 1);
            customer.setUsername(email);
            customer.setUserRole("ROLE_CUSTOMER");

            userService.saveNewUser(customer);
        }
    }

    private void seedManager(){
        List<BillyUserDto> managerList = new ArrayList<>();

        Date birthdate1 = convertToDateViaInstant(LocalDate.of(1990, Month.APRIL,15));
        managerList.add(createBasicUser("Erik de Vries", birthdate1, 0, -125));

        Date birthdate2 = convertToDateViaInstant(LocalDate.of(1963, Month.JANUARY,5));
        managerList.add(createBasicUser("José de Groot", birthdate2, 0, 0));

        saveAsManager(managerList);
    }

    private void seedBartenders(){
        List<BillyUserDto> bartenderList = new ArrayList<>();

        Date birthdate1 = convertToDateViaInstant(LocalDate.of(1980, Month.AUGUST,23));
        bartenderList.add(createBasicUser("Hans de Kraai", birthdate1, 0, 0));

        Date birthdate2 = convertToDateViaInstant(LocalDate.of(1949, Month.DECEMBER,28));
        bartenderList.add(createBasicUser("Johan van Dijk", birthdate2, 0, -100));

        Date birthdate3 = convertToDateViaInstant(LocalDate.of(2002, Month.FEBRUARY,2));
        bartenderList.add(createBasicUser("Erica van Drie", birthdate3, 0, -50));

        saveAsBartender(bartenderList);
    }

    private void seedCustomers(){
        List<BillyUserDto> customerList = new ArrayList<>();

        Date birthdate1 = convertToDateViaInstant(LocalDate.of(2005, Month.SEPTEMBER,13));
        customerList.add(createBasicUser("Fenna den Hartog", birthdate1, 25, 0));

        Date birthdate2 = convertToDateViaInstant(LocalDate.of(2010, Month.OCTOBER,19));
        customerList.add(createBasicUser("Finn den Hartog", birthdate2, 0, 0));

        Date birthdate3 = convertToDateViaInstant(LocalDate.of(2004, Month.MAY,13));
        customerList.add(createBasicUser("Rick ten Dam", birthdate3, 0, -20));

        Date birthdate4 = convertToDateViaInstant(LocalDate.of(2007, Month.NOVEMBER,21));
        customerList.add(createBasicUser("Steven de Kraai", birthdate4, 0, 0));

        Date birthdate5 = convertToDateViaInstant(LocalDate.of(1975, Month.JULY,17));
        customerList.add(createBasicUser("Ria de Vries", birthdate5, 0, -175));

        Date birthdate6 = convertToDateViaInstant(LocalDate.of(1967, Month.JUNE,13));
        customerList.add(createBasicUser("Els", birthdate6, 100, 0));

        Date birthdate7 = convertToDateViaInstant(LocalDate.of(1972, Month.JANUARY,13));
        customerList.add(createBasicUser("Johanna Waterman-Van der Heijden", birthdate7, 0, -50));

        saveAsCustomer(customerList);
    }

    private void seedOrder(){
        List<ProductDto> productList = assortmentService.findAllProducts();
        Collections.shuffle(productList);

        List<BillyUserDto> customerList = userService.findUsersByRole("ROLE_CUSTOMER");
        List<BillyUserDto> bartenderList = userService.findUsersByRole("ROLE_BARTENDER");

        for (BillyUserDto customer : customerList) {
            int amountOfOrders = getRandomNumberStartOnOne(RANDOM_UPPER_LIMIT_ORDER_AMOUNT);
            for (int orderNumber = 0; orderNumber < amountOfOrders; orderNumber++) {
                makeAndSaveOrder(customer, orderNumber, bartenderList, productList);
            }
        }
    }

    private void makeAndSaveOrder(BillyUserDto customer, int number,
                                  List<BillyUserDto> bartenderList, List<ProductDto> productList){
        BarOrderDto barOrderDto = new BarOrderDto();
        Collections.shuffle(bartenderList);

        Optional<BillyUserDto> optionalBartender = bartenderList.stream().findFirst();
        optionalBartender.ifPresent(barOrderDto::setBartender);

        barOrderDto.setDateTime(getDate(number));
        barOrderDto.setCustomer(customer);

        barOrderDto.setProductMap(makeProductMap(productList));

        BarOrderDto.activeOrder = barOrderDto;
        barOrderService.saveBarOrder(barOrderDto);
        BarOrderDto.clearActiveOrder();
    }

    private LocalDateTime getDate(int hour){
        int year = java.time.Year.now().getValue();
        int month = MonthDay.now().getMonthValue();
        int day = MonthDay.now().getDayOfMonth();
        int minute = LocalTime.now().getMinute();
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    private Map<ProductDto, Integer> makeProductMap(List<ProductDto> productList){
        Map<ProductDto, Integer> productMap = new HashMap<>();

        int buyListSize = getRandomNumberStartOnOne(RANDOM_UPPER_LIMIT_NUMBER_OF_PRODUCTS);
        ArrayList<Integer> productsAdded = new ArrayList<>();

        for (int i = 0; i < buyListSize; i++) {
            Optional<ProductDto> optionalProduct = getRandomProduct(productList, productsAdded);
            optionalProduct.ifPresent(productDto ->
                    productMap.put(productDto, getRandomNumberStartOnOne(RANDOM_UPPER_LIMIT_PRODUCT_AMOUNT)));
        }
        return productMap;
    }

    private int getRandomNumberStartOnOne(int upperLimit){
        return (int) (Math.random() *
                (upperLimit - RANDOM_LOWER_LIMIT)) + RANDOM_LOWER_LIMIT;
    }

    private Optional<ProductDto> getRandomProduct(List<ProductDto> productList, ArrayList<Integer> productsAdded){
        int productPosition = (int) (Math.random() * (productList.size() - RANDOM_LOWER_LIMIT));
        if(!productsAdded.contains(productPosition)){
            ProductDto productDto = productList.get(productPosition);
            productsAdded.add(productPosition);
            return Optional.of(productDto);
        }
        return Optional.empty();
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

        defaultBier.setProductName("Amstel Radler");
        defaultBier.setProductPrice(2.50);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Leffe Blond");
        defaultBier.setProductPrice(4.50);
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


        defaultBier.setProductName("Amstel Radler 0.0%");
        defaultBier.setProductPrice(2.25);
        defaultBier.setProductOfAge(false);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Heineken 0.0%");
        defaultBier.setProductPrice(2.25);
        defaultBier.setProductOfAge(false);
        assortmentService.saveProduct(defaultBier);

        defaultBier.setProductName("Leffe Blond 0.0%");
        defaultBier.setProductPrice(3.50);
        defaultBier.setProductOfAge(false);
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

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
