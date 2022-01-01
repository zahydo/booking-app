docker compose down
docker run --rm --name booking_app_db_container -e MYSQL_ROOT_PASSWORD=12345678 -e MYSQL_DATABASE=booking_app_db -d -p 3307:3306 mysql
echo "Waiting for mysql starting. To build the .jar..."
sleep 10
mvn clean package -f api/pom.xml
docker rm -f booking_app_db_container
docker compose up --build -d