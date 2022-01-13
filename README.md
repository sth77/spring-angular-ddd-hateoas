# spring-angular-ddd-hateoas

Code from my talk and blog post "Building better GUIs using DDD and Spring HATEOAS"

## Dependencies / Features

* Spring Data JDBC for Persistence (well suited for persisting DDD-style aggregates)
* Spring Rest Repositories to publish a HAL REST API
* Integration test showing how presence and absence of links can be tested (credits to O. Drotbohm!)
* Lombok for less boiler plate code
* frontend-maven-plugin and Spring MVC configuration to build and serve the Angular app out of Spring Boot
* Spring Boot DevTools for auto reload of the backend on save
* Angular 12 (for GUI)

## Business case

A product manager submits a production order to the system (Operation "submit"). If not submitted yet, the production order can be renamed.
A manufacturer accepts the production order (Operation "accept"), indicating the expected delivery date, which must be in the future.

All three operations are modelled as HAL links on the production orders in the REST API.
The GUI uses the links to render or hide the button which triggers the respective action.

## Benefits Frontend

* UI agnostic of URLs (only link relations)
* “dumb” engine, displaying whatever is enabled by the backend (“feature toggle”)
* Self link replaces need for aggregate ID

## Benefits Backend

* Business logic fully encapsulated in backend
* Full control over state transitions directly in aggregate
* Business capabilities expressed in the API

## How to run

> ./mvnw clean spring-boot:run

Open http://localhost:8080 in a web browser to see the GUI
Open http://localhost:8080/api to see the capabilities of the API. Follow the links to resources and actions.

## License

MIT
