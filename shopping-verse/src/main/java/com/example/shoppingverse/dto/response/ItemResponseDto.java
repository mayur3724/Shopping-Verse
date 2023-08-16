package com.example.shoppingverse.dto.response;

import com.example.shoppingverse.Enum.ProductCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class ItemResponseDto {
    String itemName;
    int quantityAdded;
    int itemPrice;
    ProductCategory category;
}
