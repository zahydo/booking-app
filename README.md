# Booking Request System	

This system allows to create and get Booking Requests for an important tourism company.

Currently, the registration of reservations is done through email or telephone calls with consultants, which has caused inconvenience to some clients due to the fact that some requests take a long time to confirm.
The technology area requires implementing a system that allows the registration of reservations using a message broker (Kafka, Rabbit MQ, etc.) which will reduce the current waiting time. Good handling of possible errors must be taken into account, whether due to a message with invalid information or possible problems in the network.


# Components

This system is composed of the next components:
![Deployment Diagram - Development](docs/uml/Deployment%20Diagram%20-%20Development.jpg)

The general architecture pattern used is Microservices, but also, the event-oriented architectural style was used to handle the Publisher/Subscriber pattern.
## Publisher


This component exposes an API to receive the following requests from the Client:

- Get all the Booking Requests.
- Get a specific Booking Request.
- Create a Booking Request.

In the next diagram, you can check how it is sharing data with the other components.
![SequenceDiagram - Producer](docs/uml/SequenceDiagram%20-%20Producer.jpg)
## Consumer

This component consumes the events published from the Publisher component using the RabbitMQ Broker and sends the request to the API to finally create the Booking Requests. See the following diagram.
![SequenceDiagram - Consumer](docs/uml/SequenceDiagram%20-%20Consumer.jpg)

## RabbitMQ Server

Broker used to define the publish/subscriptor pattern. This helps to set an event-oriented architecture. Also, a Dead Letter Queue was implemented to catch events with exceptions and retry them.

## API

This is the final interface, where the data is validated and stored. A REST API (with hateoas) is exposed to get the following actions:

- Get all the Booking Requests
- Get a specific Booking Request
- Create a Booking Request

This component was defined using the layers' architectural style. The components in each layer were organized as the following diagram shows.
![Class Diagram](docs/uml/Class%20Diagram.jpg)

## MySQL DB

Database engine used to handle the data from the API

# C4 model

The C4 model was used to represent the architecture at different abstraction levels:

1. System: shows the external systems used and the interaction with users
2. Container: shows the high-level components interacting to complete a request.
3. Component: shows how is organized a specific component.

## System Diagram

![Booking App C4 Models-System Context Diagram](docs/c4/Booking%20App%20C4%20Models-System%20Context%20Diagram.jpg)

## Container Diagram

![Booking App C4 Models-Container Diagram](docs/c4/Booking%20App%20C4%20Models-Container%20Diagram.jpg)

## Component Diagram


![Booking App C4 Models-Component Diagram](docs/c4/Booking%20App%20C4%20Models-Component%20Diagram.jpg)

# Deployment

The technologies used to deploy easily all the components in the development environment were the next:

1. Maven: to install the dependencies and package into .jar files
2. Docker: to ease the deployment process
3. Bash: to create a script to automate the deployment process

## Add application.properties

You have to set up the application.properties file to the following SpringBoot projects:

### api

 ```
#Remove of this file the .example extension and set the values in <>
#DATABASE setup
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${DATABASE_HOST:localhost}:${DATABASE_PORT:3307}/${DATABASE_NAME:booking_app_db}
spring.datasource.username=${DATABASE_USER:root}
spring.datasource.password=${DATABASE_PASSWORD:12345678}
spring.datasource.driver-class-name =com.mysql.cj.jdbc.Driver
#spring.jpa.show-sql: true
#MAIL setup
spring.mail.host=smtp.gmail.com
spring.mail.port=587
#Need a configured gmail account. Please check https://support.google.com/accounts/answer/185833
spring.mail.username=${MAIL_USERNAME:<USERNAME>}
spring.mail.password=${MAIL_PASSWORD:<APP_PASSWORD>}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

You need to have a Gmail account properly configured to get an app password. Please visit this [link](https://support.google.com/accounts/answer/185833)

### consumer

```
spring.main.web-application-type=none
spring.rabbitmq.listener.simple.retry.enabled: true
spring.rabbitmq.listener.simple.retry.initial-interval: 3s
spring.rabbitmq.listener.simple.retry.max-attempts: 5
spring.rabbitmq.listener.simple.retry.max-interval: 10s
spring.rabbitmq.listener.simple.retry.multiplier: 2
spring.rabbitmq.host:${RABBITMQ_HOST:localhost}
spring.rabbitmq.port:${RABBITMQ_PORT:5672}
```

### producer

```
server.port=8081
spring.rabbitmq.host:${RABBITMQ_HOST:localhost}
spring.rabbitmq.port:${RABBITMQ_PORT:5672}
```

## Run deploy.sh

In the terminal run the following command:

```bash
sh deploy.sh
```

## Test the Producer|Backend API

Check the next [Postman Collection](docs/BookingRequest%20API%20-%20Postman%20Collection.postman_collection.json) to test the endpoints.

# Author
Santiago Hyun Dorado. [@sahydo](https://sahydo.com/)