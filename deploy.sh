#!/bin/bash

echo "STEP 1: Clean existing containers"
docker compose down
echo "STEP 2: Create a temporary MySQL DB to build the API .jar"
docker run --rm --name booking_app_db_container -e MYSQL_ROOT_PASSWORD=12345678 -e MYSQL_DATABASE=booking_app_db -d -p 3307:3306 mysql
echo "STEP 3: Build the .jars"
mvn clean package -f consumer/pom.xml
mvn clean package -f producer/pom.xml
mvn clean package -f api/pom.xml
echo "STEP 4: Remove temporary MySQL DB"
docker rm -f booking_app_db_container
echo "STEP 5: Up the containers"
docker compose up --build -d
echo "STEP 6 (Optional): Check the logs, replacing <CONTAINER_NAME> in the following command: docker logs -f <CONTAINER_NAME>"
echo "STEP 7: Creates the first Booking Request to create the exchanges and the queues in the RabbitMQ server"
echo "curl --location --request POST 'localhost:8081/bookingRequests' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        \"checkInDate\": \"2022-12-04\",
        \"checkOutDate\": \"2022-12-05\",
        \"holderEmail\": \"santiagodorado@unicauca.edu.co\",
        \"holderName\": \"Santiago\",
        \"numberOfPeople\":\"2\",
        \"numberOfRooms\": \"1\",
        \"numberOfMinors\": \"1\"
    }'"