package com.ssg.order_management.service;

import com.ssg.order_management.dto.*;
import com.ssg.order_management.entity.*;
import com.ssg.order_management.exception.OrderManagementException;
import com.ssg.order_management.repository.OrderItemRepository;
import com.ssg.order_management.repository.OrderMasterRepository;
import com.ssg.order_management.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderMasterRepository orderMasterRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_Success() {
        // given
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        requestDto.setItems(Arrays.asList(new OrderItemDto(1L, 2)));

        Product product = new Product();
        product.setId(1L);
        product.setStock(10);
        product.setPrice(1000);
        product.setDiscountPrice(100);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderMasterRepository.save(any(OrderMaster.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        OrderCreateResponseDto response = orderService.createOrder(requestDto);

        // then
        assertNotNull(response);
        assertEquals(1, response.getItems().size());
        assertEquals(1800, response.getTotalPrice()); // (1000 - 100) * 2 = 1800
        verify(productRepository).save(product); // 재고 업데이트 확인
    }

    @Test
    void createOrder_ProductNotFound() {
        // given
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        requestDto.setItems(Arrays.asList(new OrderItemDto(1L, 2)));

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        OrderManagementException exception = assertThrows(OrderManagementException.class,
                () -> orderService.createOrder(requestDto));
        assertEquals("PRODUCT_NOT_FOUND", exception.getErrorCode().name());
    }

    @Test
    void cancelOrderItem_Success() {
        // given
        OrderItem orderItem = new OrderItem();
        orderItem.setCancelled(false);
        orderItem.setQuantity(2);
        orderItem.setFinalPrice(1800);
        Product product = new Product();
        product.setStock(10);
        orderItem.setProduct(product);
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setTotalPrice(2000);
        orderItem.setOrderMaster(orderMaster);

        when(orderItemRepository.findByIdAndProductId(1L, 1L)).thenReturn(Optional.of(orderItem));

        // when
        OrderCancelResponseDto response = orderService.cancelOrderItem(1L, 1L);

        // then
        assertEquals(2000 - 1800, response.getRemainTotalPrice());
        assertTrue(orderItem.getCancelled());
        assertEquals(2 + 10, product.getStock()); // 재고 복원 확인
        verify(orderMasterRepository).save(orderMaster);
    }

    @Test
    void getOrderDetails_Success() {
        // given
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setId(1L);
        orderMaster.setTotalPrice(2000);
        OrderItem orderItem = new OrderItem();
        orderItem.setFinalPrice(1800);
        orderItem.setCancelled(false);
        Product product = new Product();
        product.setId(2L);
        orderItem.setProduct(product);
        orderMaster.setItems(Arrays.asList(orderItem));

        when(orderMasterRepository.findById(1L)).thenReturn(Optional.of(orderMaster));

        // when
        OrderDetailResponseDto response = orderService.getOrderDetails(1L);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getOrderId());
        assertEquals(2000, response.getTotalPrice());
        assertEquals(1, response.getItems().size());
        assertEquals(2L, response.getItems().get(0).getProductId());
    }
}

