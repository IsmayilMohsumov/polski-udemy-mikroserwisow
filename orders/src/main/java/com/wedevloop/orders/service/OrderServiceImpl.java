package com.wedevloop.orders.service;

import com.wedevloop.customers.entity.Customer;
import com.wedevloop.menus.entity.Menu;
import com.wedevloop.orders.constants.OrderConstants;
import com.wedevloop.orders.dto.CustomerDTO;
import com.wedevloop.orders.dto.MenuDTO;
import com.wedevloop.orders.dto.OrderResponseDTO;
import com.wedevloop.orders.entity.Order;
import com.wedevloop.orders.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Override
    public Order createOrder(Order order) {
        // Sprawdzamy, czy klient istnieje
        String customerUrl = "http://localhost:8080/api/customer/" + order.getCustomerId();
        ResponseEntity<Customer> customerResponse = restTemplate.getForEntity(customerUrl, Customer.class);

        if (!customerResponse.getStatusCode().is2xxSuccessful() || customerResponse.getBody() == null) {
            throw new RuntimeException(OrderConstants.CUSTOMER_NOT_FOUND);
        }

        // Sprawdzamy, czy menu istnieje
        String menuUrl = "http://localhost:8081/api/menu/" + order.getMenuId();
        ResponseEntity<Menu> menuResponse = restTemplate.getForEntity(menuUrl, Menu.class);

        if (!menuResponse.getStatusCode().is2xxSuccessful() || menuResponse.getBody() == null) {
            throw new RuntimeException(OrderConstants.MENU_NOT_FOUND);
        }

        return orderRepository.save(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::mapToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(OrderConstants.ORDER_NOT_FOUND));

        return mapToOrderResponseDTO(order);
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(OrderConstants.ORDER_NOT_FOUND));

        existingOrder.setQuantity(order.getQuantity());
        existingOrder.setTotalPrice(order.getTotalPrice());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(OrderConstants.ORDER_NOT_FOUND));

        orderRepository.delete(order);
    }


    private OrderResponseDTO mapToOrderResponseDTO(Order order) {
        String customerUrl = "http://localhost:8080/api/customer/" + order.getCustomerId();
        CustomerDTO customer = restTemplate.getForObject(customerUrl, CustomerDTO.class);

        String menuUrl = "http://localhost:8081/api/menu/" + order.getMenuId();
        MenuDTO menu = restTemplate.getForObject(menuUrl, MenuDTO.class);

        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .customer(customer)
                .menu(menu)
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}