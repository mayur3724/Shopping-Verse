package com.example.shoppingverse.model;

import com.example.shoppingverse.Enum.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    @Column(unique = true)
    String mobNo;
    @Column(unique = true)
    String emailId;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    Cart cart;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Card > card=new ArrayList<>();
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<OrderEntity>orderEntity = new ArrayList<>();

}
