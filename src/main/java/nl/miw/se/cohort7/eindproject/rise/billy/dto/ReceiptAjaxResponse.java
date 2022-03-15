package nl.miw.se.cohort7.eindproject.rise.billy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jordy Pragt <j.pragt@st.hanze.nl>
 * Response Object for Ajax featuring a ProductDto's of an Order and a message.
 */
@Getter @Setter
public class ReceiptAjaxResponse {

    private String totalOrderPrice;

    private List<ReceiptProductDto> receiptList;

    private String message;

    public void createReceiptList(){
        List<ReceiptProductDto> addList = new ArrayList<>();

        for (ProductDto productDto : BarOrderDto.activeOrder.getProductMap().keySet()) {
            ReceiptProductDto receiptProductDto = new ReceiptProductDto();
            receiptProductDto.setProduct(productDto);
            receiptProductDto.setAmount(BarOrderDto.activeOrder.getProductMap().get(productDto));
            receiptProductDto.setPriceDisplay(BarOrderDto.activeOrder.getSubTotalDisplayString(productDto));
            addList.add(receiptProductDto);
        }
        setReceiptList(addList);
    }
}
