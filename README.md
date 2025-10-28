# glitch-loves-wrasslin
Wrestling video clip database project

## Requirements
- mvn
- java 25 (Recommended: use https://sdkman.io to install both java and maven)
- docker (for mysql, although an H2 impl has bee provided, you'd need to activate its profile)

## Quickstart
### VSCode Launch
Using VSCode you can build and launch the app using a `.vscode/launch.json` file like such:
```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Launch",
      "request": "launch",
      "mainClass": "dev.glitch.wrasslin.SpringBootApp",
      "projectName": "glitch-loves-wrasslin",
      "args": "",
      "vmArgs": [
        "-Dspring.profiles.active=local,swagger,mysql"
      ],
      "envFile": "${workspaceFolder}/env.local"
    }
  ]
}
```
### env.local file for env vars
You will need to set a few env vars in either an env.local or a .env file (have it match your vscode launch file).  
It should contain:
- MYSQL_USER="anyone-you-want"
- MYSQL_USER_PASSWORD="setYourPasswordHere"

You could also use docker secrets but you'd have to update the application-mysql.yml stuff to set the user/pass accordingly.
Docker secrets require a file on disk somewhere and I didn't know a good place to put it.

```env.local
MYSQL_USER="anyone-you-want"
MYSQL_USER_PASSWORD="setYourPasswordHere"
```

### MySQL via docker
A docker-compose.yml file has been included for MySQL.  You need to create a few things first:
- `mysql-root-docker-secret` -> file holding the root mysql user password
- `mysql-user-docker-secret` -> file holding the user's password
- `.env-local` or `.env` file holding the `MYSQL_USER=anyone-you-want` env variable
  - `.env` is the default arg for docker, but I prefer --env-file env.local for better visibility ;)

You can fire it up via:  
`docker compose -f docker-compose.yml --env-file env.local up`

The src/main/resources/schema.sql should create the `video` table for you in MySQL on first run

### Running and data
You can use the sample data to prime the DB.

1. Use swagger http://localhost:8080/swagger-ui/index.html
2. Use the POST /api/videos/load endpoint with the provided sample data
3. Use the GET /api/videos/search endpoint to search for videos using MySQLs built-in full-text indexing.
4. Use the GET /api/videos/search* endpoints to search separate full-text indices.

## Data Model
Currently the records will have the following
- id: This will be auto-incremented so it doesn't matter what you provide; it'll be overwritten
- url: The url that has the video clip
- position: Generally "top, bottom, neutral" and more specific can be "top pinning", "top breakdown", etc.
- family: General family/concept type like "hand fighting"
- tags: Any type of tags you want to describe / help find the clip
- notes: Any type of author note, or context
- related: Other moves/tags/family etc that you feel the move relates to or pairs with.

In general the only thing required is the ID and URL, everything else is allowed to be null.

Indexing: position, family, tags, notes, related -> all of these are full-text-indexed individually via MySQL.
A _comprehensive/combined_ full-text-index is also created for position+family+tags+notes which the /search endoint will search in a combined fashion.

## Sample data
```json
[
  {
    "id": 0,
    "url": "https://www.instagram.com/p/DQHj8TeCRLM/",
    "position": "neutral",
    "family": "hand fighting",
    "tags": "hand fighting, clearing ties thumb block",
    "notes": "Victoria Vortex",
    "related": "collar tie"
  },
  {
    "id": 0,
    "url": "https://www.instagram.com/p/DP-RtQFD0RR/",
    "position": "top pinning",
    "family": "pinning",
    "tags": "chicken wing, pump handle, head in armpit, armbar, stack",
    "notes": "Seth Seneca",
    "related": ""
  },
  {
    "id": 0,
    "url": "https://www.instagram.com/p/DO0w4NMjm8p/",
    "position": "neutral",
    "family": "ducks and drags",
    "tags": "duck under, over tie collar",
    "notes": "thejoecalvin",
    "related": "collar tie, arm drag"
  }
]
```