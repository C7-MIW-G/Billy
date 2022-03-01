package nl.miw.se.cohort7.eindproject.rise.billy.service.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import nl.miw.se.cohort7.eindproject.rise.billy.dto.ProductViewDto;
import nl.miw.se.cohort7.eindproject.rise.billy.model.Product;
import nl.miw.se.cohort7.eindproject.rise.billy.repository.BarOrderRepository;
import nl.miw.se.cohort7.eindproject.rise.billy.service.BarOrderService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * The service layer that connects the front- and back-end of the BarOrder objects
 */
public class BarOrderServiceImplementation implements BarOrderService {

    private BarOrderRepository barOrderRepository;

    public BarOrderServiceImplementation(BarOrderRepository barOrderRepository) {
        this.barOrderRepository = barOrderRepository;
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
}
