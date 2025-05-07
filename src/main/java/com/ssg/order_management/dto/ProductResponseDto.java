package com.ssg.order_management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "상품 응답 데이터")
public class ProductResponseDto {

    @Schema(description = "상품 ID", example = "1000000001")
    private Long id;

    @Schema(description = "상품명", example = "이마트 생수")
    private String name;

    @Schema(description = "판매 가격", example = "800")
    private Integer price;

    @Schema(description = "할인 금액", example = "100")
    private Integer discountPrice;

    @Schema(description = "재고 수량", example = "1000")
    private Integer stock;
}

