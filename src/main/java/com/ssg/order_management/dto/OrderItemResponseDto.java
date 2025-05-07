package com.ssg.order_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "주문 항목 응답 데이터")
public class OrderItemResponseDto {

    @Schema(description = "주문된 상품 ID", example = "101")
    private Long productId;

    @Schema(description = "주문된 상품 수량", example = "3")
    private Integer quantity;

    @Schema(description = "상품의 실구매 금액", example = "50000")
    private Integer finalPrice;
}



