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

I created a REST/JSON web service in Java using Spring Boot (RestController) with an API that supports basic CRUD:


Producer:
* Create a new producer
* Get a list of all producers
* Get a data of a single producer
* Delete a single producer


Drug:
* Create a new drug
* Get a list of all drugs
* Get a data of a single drug
* Delete a single drug
* Update a drug


User:
* Create a new user
* Get a list of all users
* Get a data of a single user based on the address


Order:
* Placing an order
* Retrieving all orders
* Get a data of a single order
* Delete a single order
* Change the status of an order
* Each order should be recorded, have a list of drugs and also have the user’s data.
* The total value of the order is always calculated, based on the prices of the drugs in it.


# Requirements

* Implement your solution according to the above specification.
* Provide unit tests.
* Document your REST-API.
* Provide a storage solution for persisting the web service’s state.

Have a way to run the service with its dependencies (database etc) locally. 
You can use either a simple script or docker or something else. It’s up to you.
