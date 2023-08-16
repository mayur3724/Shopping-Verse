package com.example.shoppingverse.dto.response;

import com.example.shoppingverse.Enum.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class CardResponseDto {
    String customerMobile;
    String cardNo;
    //masked
    CardType cardType;
    Date validTill;

}
