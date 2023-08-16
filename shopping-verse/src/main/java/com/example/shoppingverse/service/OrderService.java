package com.example.shoppingverse.service;
import com.example.shoppingverse.Enum.ProductStatus;
import com.example.shoppingverse.dto.request.OrderRequestDto;
import com.example.shoppingverse.dto.response.OrderResponseDto;
import com.example.shoppingverse.exception.CustomerNotFoundException;
import com.example.shoppingverse.exception.InsufficienQuantityException;
import com.example.shoppingverse.exception.InvalidCardException;
import com.example.shoppingverse.exception.ProductNotFoundException;
import com.example.shoppingverse.model.*;
import com.example.shoppingverse.repository.CardRepository;
import com.example.shoppingverse.repository.CustomerRepository;
import com.example.shoppingverse.repository.OrderEntityRepository;
import com.example.shoppingverse.repository.ProductRepository;
import com.example.shoppingverse.transformer.ItemTransformer;
import com.example.shoppingverse.transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
@Service
public class OrderService {
    @Autowired
    CardService cardService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    OrderEntityRepository orderEntityRepository;
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        //public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto){
            Customer customer = customerRepository.findByEmailId(orderRequestDto.getCustomerEmail());
            if(customer==null){
                throw  new CustomerNotFoundException("Customer doesn't exist");
            }

            Optional<Product> productOptional = productRepository.findById(orderRequestDto.getProductId());
            if(productOptional.isEmpty()){
                throw new ProductNotFoundException("Product doesn't exist");
            }

            Card card = cardRepository.findByCardNo(orderRequestDto.getCardUsed());

            Date todayDate = new Date();
            if(card==null || card.getCvv()!= orderRequestDto.getCvv() || todayDate.after(card.getValidTill())){
                throw new InvalidCardException("Invalid Card");
            }

            Product product = productOptional.get();
            if(product.getAvailableQuantity() < orderRequestDto.getRequiredQuantity()){
                throw new InsufficienQuantityException("Insufficient Quantity Available");
            }
            //prepare order entity
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderId(String.valueOf(UUID.randomUUID()));
            orderEntity.setCardUsed(cardService.generateMaskedCard(orderRequestDto.getCardUsed()));
            orderEntity.setOrderTotal(orderRequestDto.getRequiredQuantity()* product.getPrice());

            Item item = ItemTransformer.itemRequestDtoToItem(orderRequestDto.getRequiredQuantity());
            item.setOrderEntity(orderEntity);
            item.setProduct(product);

            orderEntity.getItems().add(item);
            orderEntity.setCustomer(customer);

//            product.getItems().set(item);
//            customer.getOrders().add(orderEntity);

            OrderEntity savedOrder = orderEntityRepository.save(orderEntity); //will save order and item

            product.getItems().add(savedOrder.getItems().get(0));
            customer.getOrderEntity().add(savedOrder);

//            productRepository.save(product);
//            customerRepository.save(customer);

            sendEmail(savedOrder);

            //prepare response dto
            return OrderTransformer.OrderToOrderResponseDto(savedOrder);
        }

    public OrderEntity placeOrder(Cart cart , Card card){
        OrderEntity order = new OrderEntity();
        order.setOrderId(String.valueOf(UUID.randomUUID()));
        order.setCardUsed(cardService.generateMaskedCard(card.getCardNo()));

        int orderTotal = 0;
        for(Item item : cart.getItems()){
            Product product = item.getProduct();
            if(product.getAvailableQuantity() < item.getRequiredQuantity()){
                throw new InsufficienQuantityException("Sorry!Insufficient quantity Available for:"+product.getProductName());
            }

            int newQuantity = product.getAvailableQuantity() - item.getRequiredQuantity();
            product.setAvailableQuantity(newQuantity);
            if(newQuantity==0){
                product.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }
            orderTotal+= product.getPrice() * item.getRequiredQuantity();
            item.setOrderEntity(order);
        }
        order.setOrderTotal(orderTotal);
        order.setItems(cart.getItems());
        order.setCustomer(card.getCustomer());
        return order;
    }

    //send email for order
    public void sendEmail(OrderEntity order){
        String text ="Congrats your Order for Iphone14plus has been placed.Following are the order details : '\n'" +
                "Order No =" +order.getOrderId() + "\n" +
                "Order Total=" +order.getOrderTotal() +"\n" +
                "OrderDate=" +order.getOrderDate();

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(order.getCustomer().getEmailId());
        mail.setFrom("8976723342chirag@gmail.com");
        mail.setSubject("Order Placed");
        mail.setText(text);
        javaMailSender.send(mail);
    }
}
