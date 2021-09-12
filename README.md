# Spring Boot Crypto API Application

This is Spring Boot maven project exposes API to register user, retrieve coins and ticker.

## Build and run

To build and run the application
```
mvn clean install

mvn spring-boot:run
```

## API Documentation

Once application is up and running, api swagger documentation will be available at

```
http://localhost:9090/api/swagger-ui.html
``` 

## H2 Database

The h2 database console will be available at
```
http://localhost:9090/api/h2-console
``` 
with `admin/admin` as a database username and password and `jdbc:h2:mem:crypto-db` as JDBC URL
