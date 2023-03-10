package com.api.orderservice.service;

import com.api.orderservice.dto.OrderLineItemsDto;
import com.api.orderservice.dto.OrderRequest;
import com.api.orderservice.model.Order;
import com.api.orderservice.model.OrderLineItems;
import com.api.orderservice.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public void placeOrder(OrderRequest orderRequest) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderItems =  orderRequest.getOrderLineItemsList().stream()
            .map(this::mapToDto)
            .toList();

        order.setOrderLineItemsList(orderItems);

        repository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }

}
