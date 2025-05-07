package com.ssg.order_management.repository;

import com.ssg.order_management.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, Long> {
    // 주문 및 주문 세부 정보 조회
    Optional<OrderMaster> findById(Long id);
}


