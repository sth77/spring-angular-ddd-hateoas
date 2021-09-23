# spring-angular-ddd-hateoas

Code from my talk "Building better GUIs using DDD and Spring HATEOAS"

## Dependencies / Features

* Spring Data JDBC for Persistence (well suited for persisting DDD-style aggregates)
* Spring Rest Repositories to publish a HAL REST API
* Lombok for less boiler plate code
* Spring Boot DevTools for auto reload of the backend on save
* Angular 12 (for GUI)

## Business case

A product manager submits a production order to the system (Operation "submit")
A manufacturer accepts the production order (Operation "accept")

Both operations are modelled as HAL links on the production orders in the REST API.
The GUI uses the links to render or hide the button triggering the respective action.

## Benefits Frontend

* UI agnostic of URLs (only link relations)
* “dumb” engine, displaying whatever is enabled by the backend (“feature toggle”)
* Self link replaces need for aggregate ID

## Benefits Backend

* Business logic fully encapsulated in backend
* Full control over state transitions directly in aggregate
* Business capabilities expressed in the API

## How to run

1) Start the Spring application (mvn spring-boot:run)
2) Start the Angular frontend (cd src/main/frontend, npm start)

The backend is served on localhost:8080/api, the frontend on localhost:4200

## License

MIT
