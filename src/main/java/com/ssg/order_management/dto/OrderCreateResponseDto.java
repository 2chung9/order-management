package com.ssg.order_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "주문 생성에 대한 응답 데이터")
public class OrderCreateResponseDto {

    @Schema(description = "생성된 주문 번호", example = "123")
    private Long orderId;

    @Schema(description = "주문된 상품 리스트"
            , example = "[{\"productId\":1,\"productName\":\"상품1\",\"quantity\":2,\"price\":10000}]")
    private List<OrderItemResponseDto> items;

    @Schema(description = "전체 주문 금액 (각 상품 금액들의 합)", example = "20000")
    private Integer totalPrice;
}