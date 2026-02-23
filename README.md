# Hotel Availability API

REST API for tracking hotel search queries. Built with Spring Boot, Kafka, and MongoDB.

## Requirements

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose

## Setup

Start the required services (MongoDB and Kafka):

```bash
docker-compose up -d
```

Give Kafka about 30 seconds to initialize properly. Then build and run:

```bash
mvn clean install
mvn spring-boot:run
```

Server runs on port 8080.

## API Endpoints

**POST /search**

Creates a new search record. Returns a unique search ID.

Request body:
```json
{
  "hotelId": "1234aBc",
  "checkIn": "29/12/2023",
  "checkOut": "31/12/2023",
  "ages": [30, 29, 1, 3]
}
```

Response:
```json
{
  "searchId": "generated-uuid-here"
}
```

**GET /count?searchId={id}**

Returns how many similar searches exist. Searches are considered similar when they have the same hotel, check-in/out dates, and guest ages (order doesn't matter).

Response:
```json
{
  "searchId": "generated-uuid-here",
  "search": {
    "hotelId": "1234aBc",
    "checkIn": "29/12/2023",
    "checkOut": "31/12/2023",
    "ages": [1, 3, 29, 30]
  },
  "count": 12
}
```

## Testing

Run the test suite:

```bash
mvn test
```

Or use the included Postman collection (`Hotel_Availability_API.postman_collection.json`) for manual testing.

## How it works

When you POST a search, it gets sent to a Kafka topic. A consumer picks it up and saves it to MongoDB. This happens pretty fast but it's async, so give it a second or two before querying the count endpoint.

The count endpoint looks for searches with matching hotel ID, dates, and guest ages. Ages get sorted before comparison so [30, 29, 1, 3] matches [1, 3, 29, 30].

## Cleanup

Stop all services:

```bash
docker-compose down
```
