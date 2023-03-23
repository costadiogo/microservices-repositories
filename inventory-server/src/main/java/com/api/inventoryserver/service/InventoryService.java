package com.api.inventoryserver.service;

import com.api.inventoryserver.dto.InventoryResponse;
import com.api.inventoryserver.repository.InventoryRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {

        return repository.findBySkuCodeIn(skuCode).stream()
            .map(inventory ->
                InventoryResponse.builder()
                    .skuCode(inventory.getSkuCode())
                    .isInStock(inventory.getQuantity() > 0)
                    .build()
            ).toList();
    }

}
