# glitch-loves-wrasslin
Wrestling video clip database project

## Requirements
- mvn
- java 25 (IMHO: use https://sdkman.io to install both java and maven)

## Quickstart
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
        "-Dspring.profiles.active=local,swagger"
      ]
    }
  ]
}
```

You can use the sample data to prime the app with an in memory H2 embedded database (for now).

1. Use swagger http://localhost:8080/swagger-ui/index.html
2. Use the POST /api/videos/load endpoint with the provided sample data
3. Use the GET /api/videos/reindex endpoint to build the full-text index
4. Use the GET /api/videos/search endpoint to search for videos using H2's built-in full-text indexing.

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