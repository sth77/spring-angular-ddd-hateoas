package com.example.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.productionorders.ProductionOrder;
import com.example.demo.productionorders.ProductionOrders;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements ApplicationRunner {
	
	private final ProductionOrders repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		repository.save(ProductionOrder.create("Order 1"));
		repository.save(ProductionOrder.create("Order 2"));
	}	
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
