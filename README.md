# Technologies Used
* Java
* Spring Boot
* Hibernate (HQL)
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

# Endpoints (examples)


* POST	/order - create a new order
* GET	/user/all-by-address - get all users by addresses
* GET	/drug/1 - get the drug by id
* GET /order/changeStatus/1 - change the status by order id 
* PUT /drug/2 - update the drug by id
* DELETE /producer/1 - delete producer by id


# Tests

* JUnit 5
* Mockito
* MockMvc
* Spring Boot Test




