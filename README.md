# GitHub Analyzer for Atipera

This Spring Boot application fetches **non-forked public repositories** from a GitHub user and returns a list including:
- repository name,
- owner login,
- all branches with their latest commit SHA.

## Endpoint

**GET** `/api/repositories/{username}`  
Returns 404 if user does not exist.

### Example response:

```json
[
  {
    "name": "guava",
    "ownerLogin": "google",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "a84d88e7554fc1fa21bcbc4efae3c782a70d2b9d"
      }
    ]
  }
]
```

## API Testing with Swagger UI

After running the application, you can test the API via Swagger at:

```
http://localhost:8080/swagger-ui/index.html
```

Make sure port 8080 is free before starting the application.

## Tech Stack

- Java 21
- Spring Boot 3.5
- RestTemplate
- JUnit 5 (integration test)

## Author

Mateusz Nawratek