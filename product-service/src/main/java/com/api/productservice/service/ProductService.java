package com.api.productservice.service;

import com.api.productservice.dto.ProductDTO;
import com.api.productservice.dto.ProductResponse;
import com.api.productservice.model.Product;
import com.api.productservice.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repository;

    public void create(ProductDTO dto) {

        Product product = Product.builder()
            .name(dto.getName())
            .description(dto.getDescription())
            .price(dto.getPrice())
            .build();

        repository.save(product);

        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {

        List<Product> products = repository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {

        return ProductResponse.builder()
            .id(product.getId())
            .description(product.getDescription())
            .name(product.getName())
            .price(product.getPrice())
            .build();
    }
}
