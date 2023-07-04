package com.kata.bananaexport.resources;


import com.kata.bananaexport.domain.Order;
import com.kata.bananaexport.domain.Recipient;
import com.kata.bananaexport.dto.RecipientDto;
import com.kata.bananaexport.services.RecipientService;
import com.kata.bananaexport.utils.RecipientManagementException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipients")
public class RecipientsResources {


    private final RecipientService recipientService;


    @Autowired
    public RecipientsResources(RecipientService recipientService) {
        this.recipientService = recipientService;
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Recipient> findAllRecipients(){
        return recipientService.findAllRecipients();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recipient createRecipient(final @Valid RecipientDto payload){
        return recipientService.createNewRecipient(payload);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Recipient updateRecipient(final @Valid RecipientDto payload, @PathVariable(value = "id") final String id) throws RecipientManagementException {
        return recipientService.updateRecipient(payload, id);
    }

    @PutMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Order> recipientOrders(@PathVariable(value = "id") final String id) throws RecipientManagementException {
        return recipientService.recipientOrders(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Recipient deleteRecipient(@PathVariable(value = "id") final String id) throws RecipientManagementException {
        return recipientService.deleteRecipient(id);
    }
}
