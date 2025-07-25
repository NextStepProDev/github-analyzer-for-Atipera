<pre>
   ___ _ _                _         _               _                    
  / _ (_) |_  /\  /\_   _| |__     /_\  _ __   __ _| |_   _ _______ _ __ 
 / /_\/ | __|/ /_/ / | | | '_ \   //_\\| '_ \ / _` | | | | |_  / _ \ '__|
/ /_\\| | |_/ __  /| |_| | |_) | /  _  \ | | | (_| | | |_| |/ /  __/ |   
\____/|_|\__\/ /_/  \__,_|_.__/  \_/ \_/_| |_|\__,_|_|\__, /___\___|_|   
                                                      |___/
</pre>

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
    "name": "github-analyzer",
    "ownerLogin": "nextsteppro",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "abc123def456"
      },
      {
        "name": "develop",
        "lastCommitSha": "789xyz987"
      }
    ]
  }
]
```

### Example error response (404 Not Found):

```json
{
  "status": 404,
  "message": "User not found"
}
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

## Cloning the repository

To get started, clone the project using GitHub CLI:

```bash
gh repo clone NextStepProDev/github-analyzer-for-atipera
```

Or using HTTPS:

```bash
git clone https://github.com/NextStepProDev/github-analyzer-for-atipera.git
```

&nbsp;

This project was created with ❤️ and commitment as a solution for the GitHub analyzer task in the Atipera recruitment
process.

## Author

Mateusz Nawratek
