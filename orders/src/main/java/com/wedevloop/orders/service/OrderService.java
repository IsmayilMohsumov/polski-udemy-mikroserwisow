package com.wedevloop.orders.service;


import com.wedevloop.orders.dto.OrderResponseDTO;
import com.wedevloop.orders.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(Long id);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
}