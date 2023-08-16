package com.example.shoppingverse.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class CartResponseDto {
    //we can use here customerResponseDto also if we want
    String customerName;
    int cartTotal;
    List<ItemResponseDto> items;
}
