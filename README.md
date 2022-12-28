[//]: # (x-release-please-start-version)

# open-discogs-api v1.5.5

[//]: # (x-release-please-end)
[![codecov](https://codecov.io/gh/sweatboys/open-discogs-api/branch/main/graph/badge.svg)](https://codecov.io/gh/sweatboys/open-discogs-api)
![release](https://github.com/sweatboys/open-discogs-api/actions/workflows/release.yml/badge.svg)[![CodeQL](https://github.com/sweatboys/open-discogs-api/actions/workflows/codeql.yml/badge.svg)](https://github.com/sweatboys/open-discogs-api/actions/workflows/codeql.yml)

An open source API for Discogs data. Expect to be matured on V2 😇

### List of upcoming features
⚡︎ Observability

⚡︎ GitHub Wiki

⚡︎ Spring Native build with Spring Boot 3

### Summary
Application

### Resources
Need Local DB? Refer to: [Go Discogs](https://github.com/state303/go-discogs)

For API Documentation: [Open Discogs API Swagger](https://api.opendiscogs.com/)

For ER Diagram: [ER Diagram](https://dbdocs.io/state303/OpenDiscogs)

## For k8s deployment
https://github.com/sweatboys/charts

Please refer to /open-discogs-api/values.yaml for details.

## For -jar deployment 
### Environment Variables
| KEY                    | VALUE               | DEFAULT               | DESC                          |
|------------------------|---------------------|-----------------------|-------------------------------|
| API_DB_HOST            | hostname:port       | None                  | Don't add r2dbc:// or jdbc:// |
| API_DB_USERNAME        | user of DB          | None                  | Read Only user                |
| API_DB_PASSWORD        | password of DB      | None                  | Password for the user         |
| API_DB_DATABASE        | database name       | discogs               | Database name                 |
| API_SERVER_URL         | http(s)://host:port | http://localhost:8080 | Deployment                    |
| SERVER_PORT            | port                | 8080                  | Application Port              |
| MANAGEMENT_SERVER_PORT | port                | 8081                  | Management Port               |


