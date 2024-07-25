# Repo-lister

Repo-lister is a Spring Boot application providing an API that lists all non-fork GitHub repositories. For each repository it also returns all branches with info about each branch's name and last commit SHA.

## Installation
1. Clone the repository:
    ```sh
    git clone https://github.com/olafmyszak/repo-lister.git
    cd repo-lister
    ```
2. Build the project:
    ```sh
    ./gradlew build
    ```

## Usage
To run the application, use the following command:
```sh
./gradlew bootRun
```
The application will start on http://localhost:8080.

## API Endpoints

### List Non-Fork Repositories

- **URL**: `/users/{username}/repos`
- **Method**: GET
- **Headers**:
  - `Accept: application/json`
- **Response**:
  - 200 OK: List of non-fork repositories with branch details.
  - 404 Not Found: User does not exist.

**Example Request**:

```http
GET /users/olafmyszak/repos HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Example Response**:

```json
[
    {
        "repositoryName": "image-transformation",
        "ownerLogin": "olafmyszak",
        "branches": [
            {
                "name": "main",
                "lastCommitSha": "95a1384d6f383564ceb64d7e2e9e3495c15d3124"
            },
            {
                "name": "v3",
                "lastCommitSha": "b048cb21ce8313a5461a268969d120256a3561f3"
            }
        ]
    },
    {
        "repositoryName": "lenstra",
        "ownerLogin": "olafmyszak",
        "branches": [
            {
                "name": "main",
                "lastCommitSha": "f06f82780961cba566f19cf70dd742a4cf37eab8"
            }
        ]
    },
    {
        "repositoryName": "roguelike",
        "ownerLogin": "olafmyszak",
        "branches": [
            {
                "name": "main",
                "lastCommitSha": "b8196aedd54d00ceac51a7c15b971ddf80ea533f"
            }
        ]
    }
]
```

## License
[WTFPL](LICENSE)
