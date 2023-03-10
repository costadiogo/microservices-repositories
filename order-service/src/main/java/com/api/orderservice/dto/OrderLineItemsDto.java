package com.api.orderservice.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderLineItemsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public OrderLineItemsDto() {
    }

    public OrderLineItemsDto(UUID id, String skuCode, BigDecimal price, Integer quantity) {
        this.id = id;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String skuCode;

    private BigDecimal price;

    private Integer quantity;

}
