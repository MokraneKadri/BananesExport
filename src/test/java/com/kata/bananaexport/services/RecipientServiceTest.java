package com.kata.bananaexport.services;
import com.kata.bananaexport.domain.Order;
import com.kata.bananaexport.domain.Price;
import com.kata.bananaexport.domain.Quantity;
import com.kata.bananaexport.domain.Recipient;
import com.kata.bananaexport.dto.RecipientDto;
import com.kata.bananaexport.repository.OrderInMemoryRepository;
import com.kata.bananaexport.repository.RecipientInMemoryRepository;
import com.kata.bananaexport.utils.RecipientManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RecipientServiceTest {

    private static final LocalDateTime DELIVERY_DATE = LocalDateTime.now().plusDays(20);
    private static final Double PRICE = 220.0;
    private static final Long WEIGHT = 25000L;
    private static final String NAME = "Alex";
    private static final String ADDRESS = "22 rue de la croquette";
    private static final String ZIP_CODE = "750015";
    private static final String CITY = "PARIS";
    private static final String COUNTRY = "France";

   private final  Recipient recipient = Recipient
            .builder()
            .name(NAME)
            .address(ADDRESS)
            .zipCode(ZIP_CODE)
            .city(CITY)
            .country(COUNTRY)
            .build();



    final RecipientInMemoryRepository recipientInMemoryRepository = new RecipientInMemoryRepository();
    final OrderInMemoryRepository orderInMemoryRepository = new OrderInMemoryRepository();

    final RecipientService serviceUnderTest = new RecipientService(recipientInMemoryRepository, orderInMemoryRepository);

    @BeforeEach
    public void setup() {
       var savedRecipient =  recipientInMemoryRepository.save(recipient);
        final Order order = Order.builder()
                .deliveryDate(DELIVERY_DATE)
                .price(Price.ofEuros(PRICE))
                .quantity(Quantity.ofKilos(WEIGHT))
                .recipient(savedRecipient)
                .build();
        orderInMemoryRepository.save(order);
    }

    @Test
    void findAllRecipients_should_return_all_existing_recipients() {
        var result = serviceUnderTest.findAllRecipients();
        assertThat(result).hasSize(1);
    }


    @Test
    void updateRecipient_should_throw_exception_when_no_matching_recipient_found()  {
        String wrongId ="N/A";
        var recipientDto = new RecipientDto(NAME, ADDRESS, ZIP_CODE, CITY, COUNTRY);
        assertThatThrownBy(() -> {
            var result = serviceUnderTest.updateRecipient(recipientDto, wrongId);
        }).isInstanceOf(RecipientManagementException.class)
                .hasMessageContaining("Requested Recipient could not be found!");
    }

    @Test
    void recipientOrders_should_throw_exception_when_no_matching_recipient_found()  {
        String wrongId ="N/A";
        assertThatThrownBy(() -> {
            var result = serviceUnderTest.recipientOrders(wrongId);
        }).isInstanceOf(RecipientManagementException.class)
                .hasMessageContaining("Requested Recipient could not be found!");
    }

    @Test
    void recipientOrders_should_return_the_list_of_order_for_the_given_recipient() throws RecipientManagementException {
       //given
        var recipientToUpdate = recipientInMemoryRepository.findAll().stream().findFirst().orElse(null);
        assert recipientToUpdate!=null;
        String recipientId = recipientToUpdate.getRecipientId();

        //when
        var result = serviceUnderTest.recipientOrders(recipientId);
        assertThat(result).hasSize(1);
        var uniqueElement = result.get(0);
        assertThat(uniqueElement.getPrice()).isEqualTo(Price.ofEuros(PRICE));
        assertThat(uniqueElement.getQuantity()).isEqualTo(Quantity.ofKilos(WEIGHT));
        assertThat(uniqueElement.getDeliveryDate()).isEqualTo(DELIVERY_DATE);
        assertThat(uniqueElement.getRecipient()).isEqualTo(recipient);
    }

    @Test
    void updateRecipient_should_update_recipient_and_return_newly_updated_recipient() throws RecipientManagementException {
        //given
        var recipientToUpdate = recipientInMemoryRepository.findAll().stream().findFirst().orElse(null);
        assert recipientToUpdate!=null;
        var newName = "zack";
        var recipientDto = new RecipientDto(newName, ADDRESS, ZIP_CODE, CITY, COUNTRY);

        //when
        String recipientId = recipientToUpdate.getRecipientId();
        var result = serviceUnderTest.updateRecipient(recipientDto, recipientId);

        //then
        assertThat(result.getRecipientId()).isEqualTo(recipientId);
        assertThat(result.getName()).isEqualTo(newName);
        assertThat(result.getAddress()).isEqualTo(ADDRESS);
        assertThat(result.getCity()).isEqualTo(CITY);
        assertThat(result.getZipCode()).isEqualTo(ZIP_CODE);
        assertThat(result.getCountry()).isEqualTo(COUNTRY);
    }

    @Test
    void deleteRecipient_should_throw_exception_when_no_matching_recipient_found()  {
        String wrongId ="N/A";
        assertThatThrownBy(() -> {
        var result = serviceUnderTest.deleteRecipient(wrongId);
        }).isInstanceOf(RecipientManagementException.class)
                .hasMessageContaining("Requested Recipient could not be found!");
    }

    @Test
    void deleteRecipient_should_delete_recipient_and_return_it() throws RecipientManagementException {
        //given
        var recipientToUpdate = recipientInMemoryRepository.findAll().stream().findFirst().orElse(null);
        assert recipientToUpdate!=null;

        //when
        String recipientId = recipientToUpdate.getRecipientId();
        var result = serviceUnderTest.deleteRecipient(recipientId);

        //then
        assertThat(result.getRecipientId()).isEqualTo(recipientId);
        assertThat(result.getName()).isEqualTo(NAME);
        assertThat(result.getAddress()).isEqualTo(ADDRESS);
        assertThat(result.getCity()).isEqualTo(CITY);
        assertThat(result.getZipCode()).isEqualTo(ZIP_CODE);
        assertThat(result.getCountry()).isEqualTo(COUNTRY);
    }

    @Test
    void createNewRecipient_should_create_and_return_new_recipient() {
        //given
        var name = "Maxime";
        var zipCode = "92320";
        var recipientDto = new RecipientDto(name, ADDRESS, zipCode, CITY, COUNTRY);

        //when
        var result = serviceUnderTest.createNewRecipient(recipientDto);

        //then
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getAddress()).isEqualTo(ADDRESS);
        assertThat(result.getCity()).isEqualTo(CITY);
        assertThat(result.getZipCode()).isEqualTo(zipCode);
        assertThat(result.getCountry()).isEqualTo(COUNTRY);
    }

}
