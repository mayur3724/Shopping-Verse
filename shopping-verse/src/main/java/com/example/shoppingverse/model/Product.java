package com.example.shoppingverse.model;

import com.example.shoppingverse.Enum.ProductCategory;
import com.example.shoppingverse.Enum.ProductStatus;
import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String productName;
    int price;
    @Enumerated(EnumType.STRING)
    ProductCategory category;
    int availableQuantity;
    @Enumerated(EnumType.STRING)
    ProductStatus productStatus;
    @ManyToOne
    @JoinColumn
    Seller seller;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    List<Item> items = new ArrayList<>();


}
