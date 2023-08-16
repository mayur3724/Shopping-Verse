package com.example.shoppingverse.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class OrderResponseDto {
    String orderId;//uuid
    Date orderDate;
    String cardUsed;
    int orderTotal;
    String customerName;
    List<ItemResponseDto> item;

}
