package com.example.shoppingverse.service;

import com.example.shoppingverse.Enum.ProductCategory;
import com.example.shoppingverse.dto.request.ProductRequestDto;
import com.example.shoppingverse.dto.response.ProductResponseDto;
import com.example.shoppingverse.exception.SellerNotFoundException;
import com.example.shoppingverse.model.Product;
import com.example.shoppingverse.model.Seller;
import com.example.shoppingverse.repository.ProductRepository;
import com.example.shoppingverse.repository.SellerRepository;
import com.example.shoppingverse.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductRepository productRepository;
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto){
        Seller seller = sellerRepository.findByEmailId(String.valueOf(productRequestDto.getSellerEmail()));
        if(seller==null){
            throw new SellerNotFoundException("Invalid Email");
        }
        //dto to entity
        Product product = ProductTransformer.ProductRequestDtoToProduct(productRequestDto);
        product.setSeller(seller);
        seller.getProducts().add(product);

        Seller savedSeller = sellerRepository.save(seller);
        List<Product> productList = savedSeller.getProducts();
        Product latestProduct = productList.get(productList.size()-1);

        //prepare response dto
        return ProductTransformer.ProductToProductResponseDto(latestProduct);
    }

    public List<ProductResponseDto> getProdByCategoryAndPriceGreaterThan(int price , ProductCategory category){
        List<Product>products = productRepository.getProdByCategoryAndPriceGreaterThan(price,category);
        //prepare list of response dtos
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(ProductTransformer.ProductToProductResponseDto(product));
        }
        return productResponseDtos;
    }
}
