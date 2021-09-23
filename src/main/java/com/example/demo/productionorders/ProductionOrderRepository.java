package com.example.demo.productionorders;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductionOrderRepository extends CrudRepository<ProductionOrder, Long> {

}
