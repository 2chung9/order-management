package com.ssg.order_management.exception;

public enum ErrorCode {

    PRODUCT_NOT_FOUND("E001", "해당 ID의 상품을 찾을 수 없습니다. [productId: %s]"),
    PRODUCT_STOCK_INSUFFICIENT("E002", "상품의 재고가 부족합니다. [productId: %s, stock: %d]"),
    ORDER_OR_PRODUCT_NOT_FOUND("E003", "해당 주문 또는 상품이 존재하지 않습니다."),
    ALREADY_CANCELED_PRODUCT("E004", "이미 취소된 상품입니다. [productId: %s]"),
    ORDER_NOT_FOUND("E005", "해당 ID의 주문을 찾을 수 없습니다. [orderId: %s]");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String formatMessage(Object... args) {
        return String.format(this.message, args);
    }
}


