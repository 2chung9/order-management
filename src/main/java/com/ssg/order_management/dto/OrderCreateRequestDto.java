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
@Schema(description = "주문 생성 요청 데이터")
public class OrderCreateRequestDto {

    @Schema(description = "주문 상품 리스트"
            , example = "[{\"productId\":1000000001,\"quantity\":2}]")
    private List<OrderItemDto> items;
}


