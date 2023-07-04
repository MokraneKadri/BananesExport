package com.kata.bananaexport.services;


import com.kata.bananaexport.domain.Order;
import com.kata.bananaexport.dto.OrderDto;
import com.kata.bananaexport.repository.OrderInMemoryRepository;
import com.kata.bananaexport.utils.OrderManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {


    private final OrderInMemoryRepository OrderInMemoryRepository;


    @Autowired
    public OrderService(OrderInMemoryRepository OrderInMemoryRepository) {
        this.OrderInMemoryRepository = OrderInMemoryRepository;
    }

    public List<Order> findAllOrders() {
        return OrderInMemoryRepository.findAll();
    }

    public Order updateOrder(OrderDto payload, String id) throws OrderManagementException {
        var toBeUpdated = OrderInMemoryRepository.findById(id)
                .orElseThrow(OrderManagementException::noSuchOrder);

        var updatedOrder = toBeUpdated.newInstanceWith(payload);
        return OrderInMemoryRepository.update(updatedOrder);
    }

    public Order deleteOrder(String id) throws OrderManagementException {
        var toBeUpdated = OrderInMemoryRepository.findById(id)
                .orElseThrow(OrderManagementException::noSuchOrder);
        return OrderInMemoryRepository.delete(toBeUpdated);
    }

    public Order createNewOrder(OrderDto payload) {
        final var Order = com.kata.bananaexport.domain.Order.of(payload);
        return OrderInMemoryRepository.save(Order);
    }
}
