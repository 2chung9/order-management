package com.ssg.order_management.service;

import com.ssg.order_management.dto.*;
import com.ssg.order_management.entity.OrderMaster;
import com.ssg.order_management.entity.OrderItem;
import com.ssg.order_management.entity.Product;
import com.ssg.order_management.exception.ErrorCode;
import com.ssg.order_management.exception.OrderManagementException;
import com.ssg.order_management.repository.OrderItemRepository;
import com.ssg.order_management.repository.OrderMasterRepository;
import com.ssg.order_management.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderMasterRepository orderMasterRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(ProductRepository productRepository, OrderMasterRepository orderMasterRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.orderMasterRepository = orderMasterRepository;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * 주문 생성 로직
     */
    @Transactional
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto) {
        // 1. 주문 초기화
        OrderMaster orderMaster = new OrderMaster();
        int totalPrice = 0;

        // 2. 주문 항목 처리
        for (OrderItemDto itemDto : requestDto.getItems()) {
            // 2.1 상품 정보 가져오기
            Product product = productRepository.findById(itemDto.getProductId()).orElse(null);
            if (product == null) {
                // throw new IllegalArgumentException("상품이 존재하지 않습니다. ID: " + itemDto.getProductId());
                throw new OrderManagementException(ErrorCode.PRODUCT_NOT_FOUND, itemDto.getProductId());
            }

            // 2.2 재고 확인
            if (product.getStock() < itemDto.getQuantity()) {
//                throw new IllegalStateException("상품 재고가 부족합니다. ID: " + itemDto.getProductId());
                throw new OrderManagementException(ErrorCode.PRODUCT_STOCK_INSUFFICIENT, itemDto.getProductId(), product.getStock());
            }

            // 2.3 실구매 금액 계산
            int finalPrice = (product.getPrice() - product.getDiscountPrice()) * itemDto.getQuantity();

            // 2.4 주문 항목 생성
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderMaster(orderMaster);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setFinalPrice(finalPrice);
            orderItem.setCancelled(false);

            orderMaster.getItems().add(orderItem);

            // 2.5 재고 차감
            product.setStock(product.getStock() - itemDto.getQuantity());
            productRepository.save(product);

            // 2.6 총 가격 계산
            totalPrice += finalPrice;
        }

        // 3. 주문 저장
        orderMaster.setTotalPrice(totalPrice);
        OrderMaster savedOrderMaster = orderMasterRepository.save(orderMaster);

        // 4. 결과 DTO 생성
        List<OrderItemResponseDto> itemsResponse = new ArrayList<>();
        for (OrderItem orderItem : savedOrderMaster.getItems()) {
            OrderItemResponseDto response = new OrderItemResponseDto();
            response.setProductId(orderItem.getProduct().getId());
            response.setQuantity(orderItem.getQuantity());
            response.setFinalPrice(orderItem.getFinalPrice());
            itemsResponse.add(response);
        }

        OrderCreateResponseDto responseDto = new OrderCreateResponseDto();
        responseDto.setOrderId(savedOrderMaster.getId());
        responseDto.setItems(itemsResponse);
        responseDto.setTotalPrice(totalPrice);
        
        return responseDto;
    }

    /**
     * 주문 상품 개별 취소 로직
     */
    @Transactional
    public OrderCancelResponseDto cancelOrderItem(Long orderId, Long productId) {
        // 1. 주문과 상품 정보 확인
        OrderItem orderItem = orderItemRepository.findByOrderMaster_IdAndProduct_Id(orderId, productId).orElse(null);
        if (orderItem == null) {
            // throw new IllegalArgumentException("해당 주문 또는 상품이 존재하지 않습니다.");
            throw new OrderManagementException(ErrorCode.ORDER_OR_PRODUCT_NOT_FOUND);
        }
        if (orderItem.getCancelled()) {
            throw new OrderManagementException(ErrorCode.ALREADY_CANCELED_PRODUCT, productId);
        }

        // 2. 취소 처리
        orderItem.setCancelled(true);
        orderItemRepository.save(orderItem);

        // 3. 재고 복구
        Product product = orderItem.getProduct();
        product.setStock(product.getStock() + orderItem.getQuantity());
        productRepository.save(product);

        // 4. 주문 금액 업데이트
        OrderMaster orderMaster = orderItem.getOrderMaster();
        orderMaster.setTotalPrice(orderMaster.getTotalPrice() - orderItem.getFinalPrice());
        orderMasterRepository.save(orderMaster);

        // 5. 결과 DTO 반환
        OrderCancelResponseDto responseDto = new OrderCancelResponseDto();
        responseDto.setProductId(productId);
        responseDto.setRefundAmount(orderItem.getFinalPrice());
        responseDto.setRemainTotalPrice(orderMaster.getTotalPrice());
        
        return responseDto;
    }

    /**
     * 주문 조회 로직
     */
    @Transactional
    public OrderDetailResponseDto getOrderDetails(Long orderId) {
        // 1. 주문 조회
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            // throw new IllegalArgumentException("해당 주문이 존재하지 않습니다. ID: " + orderId);
            throw new OrderManagementException(ErrorCode.ORDER_NOT_FOUND, orderId);
        }

        // 2. 결과 매핑
        List<OrderDetailItemDto> items = orderMaster.getItems().stream().map(orderItem -> {
            OrderDetailItemDto itemDto = new OrderDetailItemDto();
            itemDto.setProductId(orderItem.getProduct().getId());
            itemDto.setQuantity(orderItem.getQuantity());
            itemDto.setFinalPrice(orderItem.getFinalPrice());
            itemDto.setCancelled(orderItem.getCancelled());
            return itemDto;
        }).collect(Collectors.toList());

        OrderDetailResponseDto responseDto = new OrderDetailResponseDto();
        responseDto.setOrderId(orderMaster.getId());
        responseDto.setItems(items);
        responseDto.setTotalPrice(orderMaster.getTotalPrice());
        
        return responseDto;
    }

    /**
     * 상품 리스트 조회 로직
     */
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getDiscountPrice(),
                        product.getStock()
                ))
                .collect(Collectors.toList());
    }
}

