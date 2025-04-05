## Database Configuration
- **H2 Console**: `http://localhost:8088/api/h2-console`
    - JDBC URL: `jdbc:h2:mem:testdb`
    - Username: `csf`
    - Password: `12345`

## Technologies
- **Backend**: Java 17, Spring Boot 3
- **Database**: H2
- **Scheduling**: Spring Scheduler
- **Build Tool**: Maven

## API Endpoints

| Method | Endpoint                | Description                               | Example Request Body                                                      |
|:-------|:------------------------|:------------------------------------------|---------------------------------------------------------------------------|
| GET    | `/api/agg-price/latest` | Retrieve latest best aggregated prices    | -                                                                         |
| POST   | `/api/trans`            | Submit trade order                        | `{ "transactionType": "BUY", "tradingPair": "BTCUSDT", "quantity": 0.5 }` |
| GET    | `/api/wallet`           | Retrieve crypto currencies wallet balance | -                                                                         |
| GET    | `/api/trans`            | Retrieve trading history                  | -                                                                         |