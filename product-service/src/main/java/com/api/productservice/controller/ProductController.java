package com.api.productservice.controller;

import com.api.productservice.dto.ProductDTO;
import com.api.productservice.dto.ProductResponse;
import com.api.productservice.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductDTO productDTO) {

        service.create(productDTO);
    }

    @GetMapping("/list-products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> listAllProducts() {

        return service.getAllProducts();
    }

}
