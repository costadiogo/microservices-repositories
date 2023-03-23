package com.api.orderservice.service;

import static java.util.UUID.randomUUID;

import com.api.orderservice.dto.InventoryResponse;
import com.api.orderservice.dto.OrderLineItemsDto;
import com.api.orderservice.dto.OrderDto;
import com.api.orderservice.model.Order;
import com.api.orderservice.model.OrderLineItems;
import com.api.orderservice.repository.OrderRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderDto request) {

       Order order =  new Order();

       order.setOrderNumber(randomUUID().toString());

       List<OrderLineItems> orderLineItemsList = request.getOrderLineItemsDtoList()
           .stream()
           .map(this::mapToOrder)
           .toList();

       order.setOrderLineItemsList(orderLineItemsList);

       List<String> skuCodes = order.getOrderLineItemsList().stream()
           .map(OrderLineItems::getSkuCode)
           .toList();

    /*
     * Call Inventory Service, and place order if product not is in stock.
     */
        InventoryResponse[] inventoryResponseList = webClientBuilder.build().get()
            .uri("http://localhost:5000/api/v1/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
            .retrieve()
            .bodyToMono(InventoryResponse[].class)
            .block();

        assert inventoryResponseList != null;
        boolean allProductsInStock =  Arrays.stream(inventoryResponseList).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            repository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please tray again later. " +
                "SkuCodesList In Stock: " + skuCodes);
        }
    }
    private OrderLineItems mapToOrder(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();

        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

        return orderLineItems;
    }

}
