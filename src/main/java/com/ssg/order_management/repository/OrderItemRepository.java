package com.ssg.order_management.repository;

import com.ssg.order_management.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 주문 항목 개별 조회
    Optional<OrderItem> findByOrderMaster_IdAndProduct_Id(Long orderId, Long productId);
}
