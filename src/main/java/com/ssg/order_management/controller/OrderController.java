package com.ssg.order_management.controller;

import com.ssg.order_management.dto.*;
import com.ssg.order_management.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Tag(name = "Order API", description = "주문 관리 관련 API입니다.")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "주문 생성", description = "새로운 주문을 생성하는 API로 주문 데이터를 입력받아 처리합니다.")
    public OrderCreateResponseDto createOrder(
        @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "생성할 주문의 상세 데이터") OrderCreateRequestDto requestDto) {
        return orderService.createOrder(requestDto);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "주문 상세 조회", description = "주문 ID를 이용해 특정 주문의 상세 정보를 반환합니다.")
    public OrderDetailResponseDto getOrderDetails(@Parameter(description = "조회할 주문의 고유 ID", example = "123") @PathVariable Long orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @PostMapping("/cancel/{orderId}/{productId}")
    @Operation(summary = "주문 상품 취소", description = "주문 ID와 상품 ID를 지정하여 특정 주문의 특정 상품을 취소합니다.")
    public OrderCancelResponseDto cancelOrderItem(
        @Parameter(description = "취소할 주문의 ID", example = "123") @PathVariable Long orderId,
        @Parameter(description = "취소할 상품의 ID", example = "1") @PathVariable Long productId) {
        return orderService.cancelOrderItem(orderId, productId);
    }

    @GetMapping(value = "/remain-products")
    @Operation(summary = "모든 상품 조회", description = "현재 주문 가능한 모든 상품을 반환하는 API입니다.")
    public List<ProductResponseDto> getAllProducts() {
        return orderService.getAllProducts();
    }
}