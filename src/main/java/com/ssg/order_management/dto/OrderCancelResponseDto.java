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
@Schema(description = "주문 상품 취소 응답 데이터")
public class OrderCancelResponseDto {

    @Schema(description = "취소된 상품 ID", example = "1")
    private Long productId;

    @Schema(description = "환불 처리된 금액(취소된 상품의 금액)", example = "10000")
    private Integer refundAmount;

    @Schema(description = "취소 이후 남아있는 전체 주문 금액", example = "50000")
    private Integer remainTotalPrice;
}