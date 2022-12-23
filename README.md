[//]: # (x-release-please-start-version)

# open-discogs-api v1.4.0

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
```yaml
application:
  # application name.
  name: open-discogs-api
  api:
    # api server port
    port: 8080
  management:
    # management server port
    port: 8081
  # postgres configuration
  database:
    # postgres host address including port
    host: 'localhost:5432'
    # postgres username
    user: 'fill-me'
    # postgres password
    pass: 'fill-me'
  # url with FQDN for this deployment.
  # change this and don't forget the CORS configurations!
  url: 'http://localhost:8080'
  # custom environment values for container.
  # this mainly exists for supporting OTEL java-agent.
  # values set from above (i.e. api.port) will override settings from here,
  # so only put things that you missed from above settings.
  env:
    - name: OTEL_EXPORTER
      value: none
    - name: OTEL_SERVICE_NAME
      value: open-discogs-api
    - name: OTEL_RESOURCE_ATTRIBUTES
      value: none
```

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


