package com.example.shoppingverse.dto.response;

import com.example.shoppingverse.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class CustomerResponseDto {
    String name;
    String mobNo;
    String emailId;
    Gender gender;

}
