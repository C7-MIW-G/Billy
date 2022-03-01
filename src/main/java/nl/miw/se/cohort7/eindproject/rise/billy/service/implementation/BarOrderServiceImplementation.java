package nl.miw.se.cohort7.eindproject.rise.billy.service.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.BarOrderViewDto;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductViewDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BarOrder;
import nl.miw.se.cohort7.eindproject.rise.billy.model.BillyUser;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.BarOrderRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.UserRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.service.BarOrderService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * The service layer that connects the front- and back-end of the BarOrder objects
 */

@Service
public class BarOrderServiceImplementation implements BarOrderService {

    private BarOrderRepository barOrderRepository;
    private UserRepository userRepository;

    public BarOrderServiceImplementation(BarOrderRepository barOrderRepository, UserRepository userRepository) {
        this.barOrderRepository = barOrderRepository;
        this.userRepository = userRepository;
    }

    private List<ProductViewDto> convertMapsToProductView(Map<Product, Integer> productMap,
                                                          Map<Product, Double> discountMap){
        List<ProductViewDto> productViewList = new ArrayList<>();

        for (Product product : productMap.keySet()) {
            ProductViewDto productViewDto = new ProductViewDto();

            productViewDto.setAmount(productMap.get(product));

            productViewDto.setProductId(product.getProductId());
            productViewDto.setProductName(product.getProductName());
            productViewDto.setProductPrice(product.getProductPrice());

            productViewDto.setDiscountPrice(discountMap.getOrDefault(product, 0.0));

            productViewList.add(productViewDto);
        }
        return productViewList;
    }

    private String convertListToJson(List<ProductViewDto> productList){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(productList);
    }

    private List<ProductViewDto> convertJsonToList(String productList){
        Type listType = new TypeToken<ArrayList<ProductViewDto>>(){}.getType();
        return new Gson().fromJson(productList, listType);
    }

    private BarOrder convertDtoToBarOrder(BarOrderDto barOrderDto){
        BarOrder barOrder = new BarOrder();

        barOrder.setDateTime(barOrderDto.getDateTime());
        barOrder.setTotalPrice(barOrderDto.calculateTotalOrderPrice());

        barOrder.setBartenderId(barOrderDto.getBartender().getUserId());
        barOrder.setBartenderName(barOrderDto.getBartender().getName());

        barOrder.setCustomerId(barOrderDto.getCustomer().getUserId());
        barOrder.setCustomerName(barOrderDto.getCustomer().getName());

        List<ProductViewDto> productList = convertMapsToProductView(barOrderDto.getProductMap(),
                                                                    barOrderDto.getDiscountMap());
        barOrder.setProductList(convertListToJson(productList));

        return barOrder;
    }

    private BarOrderViewDto convertBarOrderToDto(BarOrder barOrder){
        BarOrderViewDto barOrderDto = new BarOrderViewDto();

        barOrderDto.setOrderId(barOrder.getOrderId());
        barOrderDto.setDateTime(barOrder.getDateTime());
        barOrderDto.setTotalPrice(barOrder.getTotalPrice());

        barOrderDto.setBartenderId(barOrder.getBartenderId());
        barOrderDto.setBartenderName(barOrder.getBartenderName());

        barOrderDto.setCustomerId(barOrder.getCustomerId());
        barOrderDto.setCustomerName(barOrder.getCustomerName());

        barOrderDto.setProductList(convertJsonToList(barOrder.getProductList()));

        return barOrderDto;
    }

    @Override
    public List<BarOrderViewDto> findAllBarOrderOfUser(Long id) {
        Optional<BillyUser> optionalBillyUser = userRepository.findById(id);
        List<BarOrderViewDto> barOrderViewDtoList = new ArrayList<>();
        if (optionalBillyUser.isPresent()) {
            List<BarOrder> barOrderList = barOrderRepository.findAllByCustomerId(id);
            for (BarOrder barOrder : barOrderList) {
                barOrderViewDtoList.add(convertBarOrderToDto(barOrder));
            }
        }
        return barOrderViewDtoList;
    }
}
