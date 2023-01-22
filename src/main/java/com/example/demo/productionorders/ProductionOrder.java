package com.example.demo.productionorders;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.data.annotation.Id;

import lombok.Getter;

@Getter
public class ProductionOrder {

	@Id
	private Long id;
	private String name;
	private LocalDate expectedCompletionDate;
	private ProductionOrderState state;

	public static ProductionOrder create(String name) {
		var result = new ProductionOrder();
		result.name = name;
		result.state = ProductionOrderState.DRAFT;
		return result;
	}

	public boolean canRename() {
		return state == ProductionOrderState.DRAFT;
	}
	
	public ProductionOrder renameTo(String newName) {
		if (!canRename()) {
			throw new IllegalStateException("Cannot rename production order in state " + state);
		}
		name = newName;
		return this;
	}

	public boolean canSubmit() {
		return state == ProductionOrderState.DRAFT;
	}
		
	public ProductionOrder submit() {
		if (!canSubmit()) {
			throw new IllegalStateException("Cannot submit production order in state " + state);
		}
		state = ProductionOrderState.SUBMITTED;
		return this;
	}

	public boolean canAccept() {
		return state == ProductionOrderState.SUBMITTED;
	}

	public ProductionOrder accept(LocalDate expectedCompletionDate) {
		if (!canAccept()) {
			throw new IllegalStateException("Cannot accept production order in state " + state);
		}
		Objects.requireNonNull(expectedCompletionDate, "expectedCompletionDate is required to submit a production order");
		if (expectedCompletionDate.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Expected completion date must be in the future, but was " + expectedCompletionDate);
		}
		state = ProductionOrderState.ACCEPTED;
		this.expectedCompletionDate = expectedCompletionDate;
		return this;
	}

	public enum ProductionOrderState { DRAFT, SUBMITTED, ACCEPTED; }

}
