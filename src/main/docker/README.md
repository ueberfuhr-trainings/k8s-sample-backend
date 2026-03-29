# Recipes Backend Docker Image

This image provides a backend service to manage recipes, implemented with [Quarkus](https://quarkus.io/).

## Ports

| Port | Description                     |
|------|---------------------------------|
| 8080 | Application port (REST API)     |
| 9000 | Management port (Health Checks) |

## Configuration

The application is configured via environment variables.

### CORS

By default, CORS is enabled, but open for all origins.

| Environment Variable   | Description                               | Default                             |
|------------------------|-------------------------------------------|-------------------------------------|
| `CORS_ORIGINS`         | Allowed origins for cross-origin requests | `*`                                 |
| `CORS_METHODS`         | Allowed HTTP methods                      | `GET,POST,PUT,DELETE,OPTIONS`       |
| `CORS_HEADERS`         | Allowed request headers                   | `accept,authorization,content-type` |
| `CORS_EXPOSED_HEADERS` | Exposed response headers                  | `location`                          |

### Database

The container requires a PostgreSQL database. Configure the connection using the following environment variables:

| Environment Variable | Description                                          | Example                                      |
|----------------------|------------------------------------------------------|----------------------------------------------|
| `DB_URL`             | JDBC URL to the database (PostgreSQL supported only) | `jdbc:postgresql://my-database:5432/recipes` |
| `DB_USER`            | Database username                                    | `recipes`                                    |
| `DB_PASSWORD`        | Database password                                    | `recipes`                                    |

To run the container locally with a PostgreSQL, use:

```bash
docker run -i --rm \
  -p 8080:8080 \
  -p 9000:9000 \
  -e DB_URL=jdbc:postgresql://db-host:5432/recipes \
  -e DB_USER=myuser \
  -e DB_PASSWORD=mypassword \
  ralfueberfuhr/recipes-backend:latest
```

### Running without a database

If no external database is available, use the `latest-dev` tag instead. This variant
uses a built-in in-memory H2 database and requires no database configuration.
Note that all data will be lost when the container is stopped.

```bash
docker run -i --rm \
  -p 8080:8080 \
  -p 9000:9000 \
  ralfueberfuhr/recipes-backend:latest-dev
```

### API Usage

Fetch all recipes:

```bash
curl -s http://localhost:8080/recipes | jq
```

Create a recipe:

```bash
curl -s -X POST http://localhost:8080/recipes \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pasta Carbonara",
    "servings": 4,
    "duration": 30,
    "difficulty": "easy",
    "ingredients": [
      { "name": "Pasta", "quantity": 500, "unit": "grams" },
      { "name": "Eggs", "quantity": 4, "unit": "pieces" },
      { "name": "Bacon", "quantity": 200, "unit": "grams" }
    ],
    "preparation": "Cook pasta. Fry bacon. Mix eggs with cheese. Combine all ingredients."
  }' | jq
```

## Health Checks

The health endpoints run on the management port (9000):

| Endpoint                               | Description                           | Usage                                                                                       |
|----------------------------------------|---------------------------------------|---------------------------------------------------------------------------------------------|
| `http://localhost:9000/q/health`       | Overall status (Liveness + Readiness) | General health check                                                                        |
| `http://localhost:9000/q/health/live`  | Liveness probe                        | Kubernetes `livenessProbe` - checks whether the application is alive                        |
| `http://localhost:9000/q/health/ready` | Readiness probe                       | Kubernetes `readinessProbe` - checks whether the application is ready (incl. DB connection) |

### Kubernetes Example

```yaml
livenessProbe:
  httpGet:
    path: /q/health/live
    port: 9000
  initialDelaySeconds: 5
  periodSeconds: 10
readinessProbe:
  httpGet:
    path: /q/health/ready
    port: 9000
  initialDelaySeconds: 5
  periodSeconds: 10
```
