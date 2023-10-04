# Loan Projections mini App

## Prerequisites
- JDK 17        
- Maven        
- Docker

## How to run
**Pull and start the PostgreSQL Docker container**

```shell script
docker-compose up -d
```

**Run the Spring Boot Application**
```shell script
./mvnw spring-boot:run
```

## API Documentation

**Get Applicable Fees for a Loan**

Endpoint: `POST http://localhost:8080/api/v1/loans/projections/fees`

Request:
```console
curl --location 'http://localhost:8080/api/v1/loans/projections/fees' \
--header 'Content-Type: application/json' \
--data '{
    "loan_amount": 3000,
    "loan_duration": 3,
    "loan_duration_unit": "weeks", // can be in months or weeks
    "start_date": "01/11/2023",
    "installment_frequency": "weekly" // can be monthly or weekly
}'
```
**Get All Applicable Installments for a Loan**

Endpoint: `POST http://localhost:8080/api/v1/loans/projections/installments`

Request:
```console
curl --location 'http://localhost:8080/api/v1/loans/projections/installments' \
--header 'Content-Type: application/json' \
--data '{
    "loan_amount": 3000,
    "loan_duration": 3,
    "loan_duration_unit": "months", // can be in months or weeks
    "start_date": "01/11/2023",
    "installment_frequency": "monthly" // can be monthly or weekly
}'
```

## Testing

**Clean the project and build it**
```shell script
./mvnw clean install
```

**Run tests and generate code coverage reports**
```shell script
./mvnw clean test jacoco:report
```

**View the code coverage report by opening the following file in your web browser**
```shell script
 open target/site/jacoco/index.html 
```

