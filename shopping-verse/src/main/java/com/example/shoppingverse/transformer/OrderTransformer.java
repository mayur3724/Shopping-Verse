package com.example.shoppingverse.transformer;
import com.example.shoppingverse.dto.response.ItemResponseDto;
import com.example.shoppingverse.dto.response.OrderResponseDto;
import com.example.shoppingverse.model.Item;
import com.example.shoppingverse.model.OrderEntity;
import java.util.ArrayList;
import java.util.List;
import static org.hibernate.boot.model.process.spi.MetadataBuildingProcess.build;
public class OrderTransformer {
    public static OrderResponseDto OrderToOrderResponseDto(OrderEntity order){
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();
        for(Item item : order.getItems()){
            itemResponseDtoList.add(ItemTransformer.ItemToItemResponseDto(item));
        }

        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .orderDate(order.getOrderDate())
                .cardUsed(order.getCardUsed())
                .orderTotal(order.getOrderTotal())
                .customerName(order.getCustomer().getName())
                .item(itemResponseDtoList)
                .build();
    }
}
