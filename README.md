## Setup

### Dependencies
- Java 11
- PostgreSQL (running on default port 5432)

Execute script from resources/db/create_database.sql to create database and user.

### Swagger UI
Available via `http://localhost:8888/api/v1/swagger-ui.html`

## Build

```
mvn clean package
```

## Run

```
From command line:
mvn spring-boot:run
```
```
From IDE Intellij IDEA:
Right-click on ProducterApplication class and select "Run 'ProducterApplication'"
```


## Test

```
mvn test
```

```
Use requests for Postman from resources/postman_requets directory.
```

## TODO

```
- validation of input data
- exception handling
- more tests
```

