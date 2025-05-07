package com.ssg.order_management.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class OrderMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderMaster")
    private List<OrderItem> items = new ArrayList<>();  // 주문 상품 리스트

    private Integer totalPrice;  // 주문 전체 금액
}
