package com.example.demo.productionorders.web;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.productionorders.ProductionOrder;
import com.example.demo.productionorders.ProductionOrder.ProductionOrderState;
import com.example.demo.productionorders.ProductionOrders;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;

@RestController
@RequestMapping("/api/productionOrders")
@RequiredArgsConstructor
public class ProductionOrderController implements RepresentationModelProcessor<EntityModel<ProductionOrder>> {

	public static final String REL_RENAME = "rename";
	public static final String REL_SUBMIT = "submit";
	public static final String REL_ACCEPT = "accept";
	
	private final ProductionOrders productionOrders;
	
	@PostMapping("/{id}/rename")
	public ResponseEntity<?> rename(@PathVariable Long id, @RequestBody RenameRequest request) {
		return productionOrders.findById(id)
			.map(po -> productionOrders.save(po.renameTo(request.newName)))
			.map(po -> ResponseEntity.ok().body(EntityModel.of(po)))
			.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/{id}/submit")
	public ResponseEntity<?> submit(@PathVariable Long id) {
		return productionOrders.findById(id)
			.map(po -> productionOrders.save(po.submit()))
			.map(po -> ResponseEntity.ok().body(EntityModel.of(po)))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/{id}/accept")
	public ResponseEntity<?> accept(@PathVariable Long id, @RequestBody CompleteRequest request) {
		return productionOrders.findById(id)
			.map(po -> productionOrders.save(po.accept(request.expectedCompletionDate)))
			.map(po -> ResponseEntity.ok().body(EntityModel.of(po)))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@Override
	public EntityModel<ProductionOrder> process(EntityModel<ProductionOrder> model) {		
		val order = model.getContent();
		if (order.getState() == ProductionOrderState.DRAFT) {
			model.add(linkTo(methodOn(getClass()).rename(order.getId(), null)).withRel(REL_RENAME));
			model.add(linkTo(methodOn(getClass()).submit(order.getId())).withRel(REL_SUBMIT));
		}
		if (order.getState() == ProductionOrderState.SUBMITTED) {
			model.add(linkTo(methodOn(getClass()).accept(order.getId(), null)).withRel(REL_ACCEPT));
		}				
		return model;
	}
	
	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	void handleValidationException(Exception exception) {
		throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
	}
	
	@Value
	static class RenameRequest {
		@NonNull String newName;
	}

	@Value
	static class CompleteRequest {
		@NonNull LocalDate expectedCompletionDate;
	}
	
}
