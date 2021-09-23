package com.example.demo.productionorders;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.val;

@Getter
public class ProductionOrder {

	@Id
	private Long id;
	private String name;
	private ProductionOrderState state;

	public static ProductionOrder create(String name) {
		val result = new ProductionOrder();
		result.name = name;
		result.state = ProductionOrderState.DRAFT;
		return result;
	}
	
	public enum ProductionOrderState { DRAFT, SUBMITTED, ACCEPTED; }

}
