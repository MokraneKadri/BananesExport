package com.kata.bananaexport.repository;


import com.kata.bananaexport.domain.Recipient;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class RecipientInMemoryRepository implements InMemoryRepository<String, Recipient>{

    private final Map<String, Recipient> recipients = new HashMap<>();

    @Override
    public Optional<Recipient> findById(final String id){
        return Optional.ofNullable(recipients.get(id));
    }

    @Override
    public List<Recipient> findAll(){
        return recipients.values().stream().toList();
    }

    @Override
    public Recipient delete(Recipient entity) {
        return recipients.remove(entity.getRecipientId());
    }

    @Override
    public Recipient update(final Recipient order){
        var replaced = recipients.replace(order.getRecipientId(),  order);
        assert replaced!=null;
        return recipients.get(replaced.getRecipientId());
    }

    @Override
    public Recipient save(final Recipient recipient) {
        String orderId = nextKeyValue();
        recipient.setRecipientId(orderId);
        this.recipients.put(orderId, recipient);
        return recipient;
    }
}
