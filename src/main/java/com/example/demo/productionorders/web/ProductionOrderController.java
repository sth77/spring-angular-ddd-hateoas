package com.example.demo.productionorders.web;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.productionorders.ProductionOrder;
import com.example.demo.productionorders.ProductionOrder.ProductionOrderState;
import com.example.demo.productionorders.ProductionOrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/productionOrder")
@RequiredArgsConstructor
public class ProductionOrderController implements RepresentationModelProcessor<EntityModel<ProductionOrder>> {

	public static final String REL_SUBMIT = "submit";
	public static final String REL_ACCEPT = "accept";
	
	private final ProductionOrderRepository repository;
	
	@Override
	public EntityModel<ProductionOrder> process(EntityModel<ProductionOrder> model) {		
		val order = model.getContent();
		if (order.getState() == ProductionOrderState.DRAFT) {
			model.add(linkTo(methodOn(getClass()).submit(order.getId())).withRel(REL_SUBMIT));
		}
		if (order.getState() == ProductionOrderState.SUBMITTED) {
			model.add(linkTo(methodOn(getClass()).accept(order.getId())).withRel(REL_ACCEPT));
		}				
		return model;
	}
	
	@PostMapping("/{id}/submit")
	public ResponseEntity<?> submit(@PathVariable Long id) {
		return repository.findById(id)
			.map(po -> ResponseEntity.ok().body(repository.save(po.submit())))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/{id}/accept")
	public ResponseEntity<?> accept(@PathVariable Long id) {
		return repository.findById(id)
			.map(po -> ResponseEntity.ok().body(repository.save(po.accept())))
			.orElse(ResponseEntity.notFound().build());
	}
	
}
