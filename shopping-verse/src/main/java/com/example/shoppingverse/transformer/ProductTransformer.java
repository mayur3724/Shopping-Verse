package com.example.shoppingverse.transformer;

import com.example.shoppingverse.Enum.ProductStatus;
import com.example.shoppingverse.dto.request.ProductRequestDto;
import com.example.shoppingverse.dto.response.ProductResponseDto;
import com.example.shoppingverse.model.Product;

public class ProductTransformer {
    public static Product ProductRequestDtoToProduct(ProductRequestDto productRequestDto){
        return Product.builder()
                .productName(productRequestDto.getProductName())
                .category(productRequestDto.getCategory())
                .availableQuantity(productRequestDto.getAvailableQuantity())
                .price(productRequestDto.getPrice())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }
    public static ProductResponseDto ProductToProductResponseDto(Product product){
        return ProductResponseDto.builder()
                .sellerName(product.getProductName())
                .productName(product.getProductName())
                .availableQuantity(product.getAvailableQuantity())
                .category(product.getCategory())
                .productStatus(product.getProductStatus())
                .price(product.getPrice())
                .build();
    }
}
