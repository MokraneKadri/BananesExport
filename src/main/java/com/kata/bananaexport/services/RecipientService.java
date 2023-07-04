package com.kata.bananaexport.services;


import com.kata.bananaexport.domain.Order;
import com.kata.bananaexport.domain.Recipient;
import com.kata.bananaexport.dto.RecipientDto;
import com.kata.bananaexport.repository.OrderInMemoryRepository;
import com.kata.bananaexport.repository.RecipientInMemoryRepository;
import com.kata.bananaexport.utils.RecipientManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipientService {


    private final RecipientInMemoryRepository recipientInMemoryRepository;
    private final OrderInMemoryRepository orderInMemoryRepository;


    @Autowired
    public RecipientService(RecipientInMemoryRepository recipientInMemoryRepository, OrderInMemoryRepository orderInMemoryRepository) {
        this.recipientInMemoryRepository = recipientInMemoryRepository;
        this.orderInMemoryRepository = orderInMemoryRepository;
    }

    public List<Recipient> findAllRecipients() {
        return recipientInMemoryRepository.findAll();
    }

    public Recipient updateRecipient(RecipientDto payload, String id) throws RecipientManagementException {
        var toBeUpdated = getIfExists(id);

        var updatedRecipient = toBeUpdated.newInstanceWith(payload);
        return recipientInMemoryRepository.update(updatedRecipient);
    }

    public Recipient deleteRecipient(String id) throws RecipientManagementException {
        var toBeUpdated = getIfExists(id);
        return recipientInMemoryRepository.delete(toBeUpdated);
    }

    private Recipient getIfExists(String id) throws RecipientManagementException {
        return recipientInMemoryRepository.findById(id)
                .orElseThrow(RecipientManagementException::noSuchRecipient);
    }

    public Recipient createNewRecipient(RecipientDto payload) {
        final var recipient = Recipient.of(payload);
        return recipientInMemoryRepository.save(recipient);
    }

    public List<Order> recipientOrders(String id) throws RecipientManagementException {
        var recipient = getIfExists(id);
        return orderInMemoryRepository.findAll()
                .stream()
                .filter(o -> o.getRecipient().getRecipientId().equals(recipient.getRecipientId()))
                .collect(Collectors.toList());
    }
}
