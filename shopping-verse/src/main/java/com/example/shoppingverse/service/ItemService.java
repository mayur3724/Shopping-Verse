package com.example.shoppingverse.service;

import com.example.shoppingverse.dto.request.ItemRequestDto;
import com.example.shoppingverse.exception.CustomerNotFoundException;
import com.example.shoppingverse.exception.InsufficienQuantityException;
import com.example.shoppingverse.exception.ProductNotFoundException;
import com.example.shoppingverse.model.Customer;
import com.example.shoppingverse.model.Item;
import com.example.shoppingverse.model.Product;
import com.example.shoppingverse.repository.CustomerRepository;
import com.example.shoppingverse.repository.ProductRepository;
import com.example.shoppingverse.transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;
    public Item createItem(ItemRequestDto itemRequestDto) {
        Customer customer = customerRepository.findByEmailId(itemRequestDto.getCustomerEmail());
        if(customer==null){
            throw new CustomerNotFoundException("Customer doesn't exist");
        }
        Optional<Product>productOptional = productRepository.findById(itemRequestDto.getProductId());
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product doesn't exist");
        }
        Product product = productOptional.get();

        //check for required quantity
        if(product.getAvailableQuantity() < itemRequestDto.getRequiredQuantity()){
            throw new InsufficienQuantityException("Sorry! Required quantity doesn't available");
        }
        //create item
        Item item = ItemTransformer.itemRequestDtoToItem(itemRequestDto.getRequiredQuantity());
        item.setProduct(product);

        return item;

    }
}
