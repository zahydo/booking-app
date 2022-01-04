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