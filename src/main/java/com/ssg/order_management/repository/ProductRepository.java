package com.ssg.order_management.repository;

import com.ssg.order_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 특정 상품의 재고 업데이트를 위한 메서드
    Optional<Product> findById(Long productId);

    // 전체 상품 리스트 조회를 위한 메서드
    List<Product> findAll();
}

