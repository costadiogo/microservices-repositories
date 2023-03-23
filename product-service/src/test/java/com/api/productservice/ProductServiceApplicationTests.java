package com.api.productservice;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.api.productservice.dto.ProductDTO;
import com.api.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository repository;

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		
		ProductDTO dto = getProductRequest();

		String dtoAsString =  objectMapper.writeValueAsString(dto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dtoAsString))
			.andExpect(status().isCreated());

		Assertions.assertEquals(1, repository.findAll().size());
	}

	private ProductDTO getProductRequest() {

		return ProductDTO.builder()
			.name("Iphone 14")
			.description("Iphone 14 Gold")
			.price(BigDecimal.valueOf(8500))
			.build();
	}

}
