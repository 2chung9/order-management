package com.ssg.order_management.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // 상품명

    private Integer price;  // 판매가격

    private Integer discountPrice;  // 할인금액

    private Integer stock;  // 재고
}
