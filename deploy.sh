docker compose down
docker run --rm --name booking_app_db_container -e MYSQL_ROOT_PASSWORD=12345678 -e MYSQL_DATABASE=booking_app_db -d -p 3307:3306 mysql
echo "Building .jars ..."
mvn clean package -f consumer/pom.xml
mvn clean package -f producer/pom.xml
mvn clean package -f api/pom.xml
docker rm -f booking_app_db_container
docker compose up --build -d