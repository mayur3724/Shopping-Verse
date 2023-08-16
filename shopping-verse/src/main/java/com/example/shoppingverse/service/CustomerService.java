package com.example.shoppingverse.service;

import com.example.shoppingverse.dto.request.CustomerRequestDto;
import com.example.shoppingverse.dto.response.CustomerResponseDto;
import com.example.shoppingverse.model.Cart;
import com.example.shoppingverse.model.Customer;
import com.example.shoppingverse.repository.CustomerRepository;
import com.example.shoppingverse.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Transformer;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) {
//        Customer customer = new Customer();
//        customer.setName(customerRequestDto.getName());
//        customer.setGender(customerRequestDto.getGender());
//        customer.setEmailId(customerRequestDto.getEmailId());
//        customer.setMobNo(customerRequestDto.getMobNo());

        //dto to entity
        Customer customer = CustomerTransformer.CustomerRequestDtoToCustomer(customerRequestDto);

        Cart cart = new Cart();
        cart.setCartTotal(0);
        cart.setCustomer(customer);

        customer.setCart(cart);

        Customer savedCustomer =  customerRepository.save(customer); //save both customer and cart

        //prepare response dto
        return CustomerTransformer.CustomerToCustomerResponseDto(savedCustomer);

    }
}
