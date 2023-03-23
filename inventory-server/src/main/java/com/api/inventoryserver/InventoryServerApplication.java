package com.api.inventoryserver;

import com.api.inventoryserver.model.Inventory;
import com.api.inventoryserver.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository repository) {

		return args -> {
			Inventory inv = new Inventory();
			inv.setSkuCode("Macbook");
			inv.setQuantity(100);

			repository.save(inv);

			Inventory inv1 = new Inventory();
			inv1.setSkuCode("Ipad_5");
			inv1.setQuantity(0);

			repository.save(inv1);
		};
	}

}
