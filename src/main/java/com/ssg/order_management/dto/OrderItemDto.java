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
@Schema(description = "주문 상품 정보")
public class OrderItemDto {

    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @Schema(description = "상품 수량", example = "2")
    private Integer quantity;
}