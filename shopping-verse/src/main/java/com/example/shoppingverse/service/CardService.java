package com.example.shoppingverse.service;
import com.example.shoppingverse.dto.request.CardRequestDto;
import com.example.shoppingverse.dto.response.CardResponseDto;
import com.example.shoppingverse.exception.CustomerNotFoundException;
import com.example.shoppingverse.model.Card;
import com.example.shoppingverse.model.Customer;
import com.example.shoppingverse.repository.CustomerRepository;
import com.example.shoppingverse.transformer.CardTransformer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
public class CardService {
    @Autowired
    CustomerRepository customerRepository;
    public CardResponseDto addCard(CardRequestDto cardRequestDto){
        Customer customer = customerRepository.findByMobNo(cardRequestDto.getMobileNo());
        if(customer==null){
            throw new CustomerNotFoundException("Customer Not Found");
        }
        //create card entity
        Card card = CardTransformer.CardRequestDtoToCard(cardRequestDto);
        card.setCustomer(customer);
       // card.setCard.add(card);

        Customer savedCustomer = customerRepository.save(customer);
        List<Card> cards = savedCustomer.getCard();
        Card latestCards = cards.get(cards.size()-1);

        //prepare card response dto
        CardResponseDto cardResponseDto = CardTransformer.CardToCardResponseDto(latestCards);
        cardResponseDto.setCardNo(generateMaskedCard(latestCards.getCardNo()));
        return cardResponseDto;
    }
    public String generateMaskedCard(String cardNo){
        int cardLength=cardNo.length();
        String maskedCard="";
        for(int i=0;i<cardLength-4;i++){
            maskedCard+="X";
        }
        maskedCard+=cardNo.substring(cardLength-4);
        return maskedCard;
    }
}
