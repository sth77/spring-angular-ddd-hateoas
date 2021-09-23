package com.example.demo.productionorders;

import java.nio.channels.IllegalSelectorException;

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
	
	public ProductionOrder submit() {
		if (state != ProductionOrderState.DRAFT) {
			throw new IllegalStateException("cannot submit PO in state " + state);
		}
		state = ProductionOrderState.SUBMITTED;
		return this;
	}

	public ProductionOrder accept() {
		if (state != ProductionOrderState.SUBMITTED) {
			throw new IllegalStateException("cannot accept PO in state " + state);
		}
		state = ProductionOrderState.ACCEPTED;
		return this;
	}

	public enum ProductionOrderState { DRAFT, SUBMITTED, ACCEPTED; }

}
