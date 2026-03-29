# Recipes Backend Docker Image

This image provides a backend service to manage recipes, implemented with [Quarkus](https://quarkus.io/).

## Ports

| Port | Description                     |
|------|---------------------------------|
| 8080 | Application port (REST API)     |
| 9000 | Management port (Health Checks) |

## Configuration

The application is configured via environment variables. Quarkus maps environment variables
to properties, e.g. `quarkus.datasource.jdbc.url` becomes `QUARKUS_DATASOURCE_JDBC_URL`.

### Database

The container requires a PostgreSQL database. Configure the connection using the following environment variables:

| Environment Variable | Description                                          | Example                                      |
|----------------------|------------------------------------------------------|----------------------------------------------|
| `DB_URL`             | JDBC URL to the database (PostgreSQL supported only) | `jdbc:postgresql://my-database:5432/recipes` |
| `DB_USER`            | Database username                                    | `recipes`                                    |
| `DB_PASSWORD`        | Database password                                    | `recipes`                                    |

#### Example with PostgreSQL

```bash
docker run -i --rm \
  -p 8080:8080 \
  -p 9000:9000 \
  -e DB_URL=jdbc:postgresql://db-host:5432/recipes \
  -e DB_USER=myuser \
  -e DB_PASSWORD=mypassword \
  ralfueberfuhr/recipes-backend:latest
```

#### Running without a database

If no external database is available, you can use an in-memory H2 database instead.
Note that all data will be lost when the container is stopped.

```bash
docker run -i --rm \
  -p 8080:8080 \
  -p 9000:9000 \
  -e DB_TYPE=h2 \
  -e DB_URL=jdbc:h2:mem:recipes \
  ralfueberfuhr/recipes-backend:latest
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
