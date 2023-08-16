package com.example.shoppingverse.transformer;

import com.example.shoppingverse.dto.request.CustomerRequestDto;
import com.example.shoppingverse.dto.response.CustomerResponseDto;
import com.example.shoppingverse.model.Customer;

//@UtilityClass
// used for throwing error if we make object of utility class,not a good habit for writing these annotations
public class CustomerTransformer {
    public static Customer CustomerRequestDtoToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder()
                .name(customerRequestDto.getName())
                .gender(customerRequestDto.getGender())
                .mobNo(customerRequestDto.getMobNo())
                .emailId(customerRequestDto.getEmailId())
                .build();
    }
    public static CustomerResponseDto CustomerToCustomerResponseDto(Customer customer){
        return CustomerResponseDto.builder()
                .name(customer.getName())
                .gender(customer.getGender())
                .mobNo(customer.getMobNo())
                .emailId(customer.getEmailId())
                .build();
    }
}
