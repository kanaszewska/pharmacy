# Technologies Used
* Java
* Spring Boot
* Hibernate
* Maven
* Rest API
* Git
* JUnit
* Mockito


# Features
*

# Problem Statement
I created a tiny REST/JSON web service in Java using Spring Boot (RestController) with an API that supports basic Products' CRUD:

* Create a new Product
* Get a list of all Products
* Update a Product

The API should also support:
* Placing an Order
* Retrieving all orders within a given time period
* A product should have a name and some representation of its price.

Each order should be recorded and have a list of products. 
It should also have the buyer’s e-mail, and the time the order was placed. 
The total value of the order should always be calculated, based on the prices of the products in it.

It should be possible to change the product’s price, but this shouldn’t affect the total value of orders which have already been placed.

# Requirements

* Implement your solution according to the above specification.
* Provide unit tests.
* Document your REST-API.
* Provide a storage solution for persisting the web service’s state.

Have a way to run the service with its dependencies (database etc) locally. 
You can use either a simple script or docker or something else. It’s up to you.
