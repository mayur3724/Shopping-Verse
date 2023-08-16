package com.example.shoppingverse.dto.response;

import com.example.shoppingverse.Enum.ProductCategory;
import com.example.shoppingverse.Enum.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class ProductResponseDto {
    String sellerName;
    String productName;
    int price;
    ProductCategory category;
    int availableQuantity;
    ProductStatus productStatus;

}
