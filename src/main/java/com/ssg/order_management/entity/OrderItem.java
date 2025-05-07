package com.ssg.order_management.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private OrderMaster orderMaster;  // 소속된 주문

    @ManyToOne
    private Product product;  // 주문 상품

    private Integer quantity;  // 개수

    private Integer finalPrice;  // 실구매 금액

    private Boolean cancelled;  // 취소 여부
}

