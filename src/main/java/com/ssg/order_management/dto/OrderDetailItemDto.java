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
@Schema(description = "주문 상세 내 상품 정보")
public class OrderDetailItemDto {

    @Schema(description = "주문 상품 ID", example = "1")
    private Long productId;

    @Schema(description = "상품 주문 수량", example = "2")
    private Integer quantity;

    @Schema(description = "상품별 실구매 금액", example = "20000")
    private Integer finalPrice;

    @Schema(description = "상품 취소 여부", example = "false")
    private Boolean cancelled;
}


