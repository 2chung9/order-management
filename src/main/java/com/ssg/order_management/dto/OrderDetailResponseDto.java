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
@Schema(description = "주문 상세 응답 정보")
public class OrderDetailResponseDto {

    @Schema(description = "주문 ID", example = "101")
    private Long orderId;

    @Schema(description = "주문 상품 상세 리스트"
            , example = "[{\"productId\":1,\"quantity\":2,\"finalPrice\":20000,\"cancelled\":false}]")
    private List<OrderDetailItemDto> items;

    @Schema(description = "주문 전체 금액", example = "100000")
    private Integer totalPrice;
}