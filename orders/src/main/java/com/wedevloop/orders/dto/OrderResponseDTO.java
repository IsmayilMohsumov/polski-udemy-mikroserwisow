package com.wedevloop.orders.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long orderId;
    private CustomerDTO customer;
    private MenuDTO menu;
    private int quantity;
    private double totalPrice;
}