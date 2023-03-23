package com.api.orderservice.controller;

import com.api.orderservice.dto.OrderDto;
import com.api.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderDto request ) {

        service.placeOrder(request);

        return "Order Placed Successfully";
    }

}
