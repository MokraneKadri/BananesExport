package com.kata.bananaexport.services;

import com.kata.bananaexport.domain.Order;
import com.kata.bananaexport.domain.Price;
import com.kata.bananaexport.domain.Quantity;
import com.kata.bananaexport.domain.Recipient;
import com.kata.bananaexport.dto.OrderDto;
import com.kata.bananaexport.repository.OrderInMemoryRepository;
import com.kata.bananaexport.utils.OrderManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderServiceTest {

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
    final OrderInMemoryRepository orderInMemoryRepository = new OrderInMemoryRepository();

    final OrderService serviceUnderTest = new OrderService(orderInMemoryRepository);

    @BeforeEach
    public void setup() {
        final var order = Order.builder()
                .deliveryDate(DELIVERY_DATE)
                .price(Price.ofEuros(PRICE))
                .quantity(Quantity.ofKilos(WEIGHT))
                .recipient(recipient)
                .build();
        orderInMemoryRepository.save(order);
    }

    @Test
    void findAllOrders_should_return_all_existing_orders() {
        var result = serviceUnderTest.findAllOrders();
        assertThat(result).hasSize(1);
    }


    @Test
    void updateOrder_should_throw_exception_when_no_matching_order_found()  {
        String wrongId ="N/A";
        var oderDto = new OrderDto(recipient, 25L, LocalDateTime.now());
        assertThatThrownBy(() -> {
            var result = serviceUnderTest.updateOrder(oderDto, wrongId);
        }).isInstanceOf(OrderManagementException.class)
                .hasMessageContaining("Requested Order could not be found!");
    }

    @Test
    void updateOrder_should_update_order_and_return_newly_updated_order() throws OrderManagementException {
        //given
        var orderToUpdate = orderInMemoryRepository.findAll().stream().findFirst().orElse(null);
        assert orderToUpdate!=null;
        var newQuantity = 25L;
        var newDelivery = LocalDateTime.now().plusDays(55);
        var oderDto = new OrderDto(recipient, newQuantity, newDelivery);

        //when
        String orderId = orderToUpdate.getOrderId();
        var result = serviceUnderTest.updateOrder(oderDto, orderId);

        //then
        assertThat(result.getOrderId()).isEqualTo(orderId);
        assertThat(result.getPrice()).isEqualTo(Price.ofEuros(newQuantity*2.5));
        assertThat(result.getQuantity()).isEqualTo(Quantity.ofKilos(newQuantity));
        assertThat(result.getDeliveryDate()).isEqualTo(newDelivery);
        assertThat(result.getRecipient()).isEqualTo(recipient);
    }

    @Test
    void deleteOrder_should_throw_exception_when_no_matching_order_found()  {
        String wrongId ="N/A";
        assertThatThrownBy(() -> {
            var result = serviceUnderTest.deleteOrder( wrongId);
        }).isInstanceOf(OrderManagementException.class)
                .hasMessageContaining("Requested Order could not be found!");
    }

    @Test
    void deleteOrder_should_delete_order_and_return_it() throws OrderManagementException {
        //given
        var orderToUpdate = orderInMemoryRepository.findAll().stream().findFirst().orElse(null);
        assert orderToUpdate!=null;

        //when
        String orderId = orderToUpdate.getOrderId();
        var result = serviceUnderTest.deleteOrder(orderId);

        //then
        assertThat(result.getOrderId()).isEqualTo(orderId);
        assertThat(result.getPrice()).isEqualTo(Price.ofEuros(PRICE));
        assertThat(result.getQuantity()).isEqualTo(Quantity.ofKilos(WEIGHT));
        assertThat(result.getDeliveryDate()).isEqualTo(DELIVERY_DATE);
        assertThat(result.getRecipient()).isEqualTo(recipient);
    }

    @Test
    void createNewOrder_should_create_and_return_new_order() {
        //given
        var quantity = 25L;
        var delivery = LocalDateTime.now().plusDays(55);
        var oderDto = new OrderDto(recipient, quantity, delivery);

        //when
        var result = serviceUnderTest.createNewOrder(oderDto);

        //then
        assertThat(result.getOrderId()).isNotEmpty();
        assertThat(result.getPrice()).isEqualTo(Price.ofEuros(oderDto.quantity()*2.5));
        assertThat(result.getQuantity()).isEqualTo(Quantity.ofKilos(oderDto.quantity()));
        assertThat(result.getDeliveryDate()).isEqualTo(oderDto.deliveryDate());
        assertThat(result.getRecipient()).isEqualTo(recipient);
    }

}
