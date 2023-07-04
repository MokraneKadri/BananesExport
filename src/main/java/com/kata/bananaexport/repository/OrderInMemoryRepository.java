package com.kata.bananaexport.repository;


import com.kata.bananaexport.domain.Order;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class OrderInMemoryRepository implements InMemoryRepository<String, Order>{

    private final Map<String, Order> orders = new HashMap<>();

    @Override
    public Optional<Order> findById(final String id){
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<Order> findAll(){
        return orders.values().stream().toList();
    }

    @Override
    public Order delete(Order entity) {
        return orders.remove(entity.getOrderId());
    }

    @Override
    public Order update(final Order order){
        var replaced = orders.replace(order.getOrderId(),  order);
       assert replaced!=null;
        return orders.get(replaced.getOrderId());
    }

    @Override
    public Order save(Order recipient) {
        String orderId = nextKeyValue();
        recipient.setOrderId(orderId);
         this.orders.put(orderId, recipient);
         return recipient;
    }

}
