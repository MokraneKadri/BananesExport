package com.kata.bananaexport.resources;


import com.kata.bananaexport.domain.Order;
import com.kata.bananaexport.dto.OrderDto;
import com.kata.bananaexport.services.OrderService;
import com.kata.bananaexport.utils.OrderManagementException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersResources {


    private final OrderService orderService;


    @Autowired
    public OrdersResources(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> findAllOrders(){
        return orderService.findAllOrders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(final @Valid OrderDto payload){
        return orderService.createNewOrder(payload);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Order updateOrder(final @Valid OrderDto payload, @PathVariable(value = "id") final String id) throws OrderManagementException {
        return orderService.updateOrder(payload, id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Order deleteOrder(@PathVariable(value = "id") final String id) throws OrderManagementException {
        return orderService.deleteOrder(id);
    }
}
