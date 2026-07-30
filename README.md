# OpenDiscogs API

A read-only WebFlux API for PostgreSQL databases populated by
[OpenDiscogs Batch](https://github.com/dsub-io/open-discogs-batch).

This is an independent DSUB project. It is not affiliated with or endorsed by
Discogs. The Discogs name is used only to identify the public data source.

[![Build](https://github.com/dsub-io/open-discogs-api/actions/workflows/build.yml/badge.svg)](https://github.com/dsub-io/open-discogs-api/actions/workflows/build.yml)
[![CodeQL](https://github.com/dsub-io/open-discogs-api/actions/workflows/codeql.yml/badge.svg)](https://github.com/dsub-io/open-discogs-api/actions/workflows/codeql.yml)
[![Release](https://github.com/dsub-io/open-discogs-api/actions/workflows/release.yml/badge.svg)](https://github.com/dsub-io/open-discogs-api/actions/workflows/release.yml)

<!-- x-release-please-start-version -->
Current version: `1.5.5`
<!-- x-release-please-end -->

## Architecture

The three OpenDiscogs repositories have separate responsibilities:

- [open-discogs-batch](https://github.com/dsub-io/open-discogs-batch) downloads,
  verifies, and imports the public monthly data dumps.
- [open-discogs-jooq](https://github.com/dsub-io/open-discogs-jooq) publishes the
  generated PostgreSQL schema model to Maven Central.
- `open-discogs-api` consumes that Maven artifact and exposes artists, labels,
  masters, and releases through a reactive HTTP API.

The API depends on:

```text
io.dsub.opendiscogs:open-discogs-jooq:0.0.5
```

No generated jOOQ sources, GitHub package credentials, or personal access
tokens are stored in this repository.

## Requirements

- JDK 21
- Docker for PostgreSQL integration tests
- A PostgreSQL database initialized by the current OpenDiscogs schema

The Gradle wrapper uses Gradle 9.6.1. The application uses Spring Boot 4.1,
jOOQ 3.21.6, PostgreSQL 18 for integration tests, and Testcontainers 2.0.5.
With SDKMAN installed, activate the repository toolchain with:

```bash
sdk env
```

## Build and test

Run the complete deterministic suite and enforce the coverage gate:

```bash
./gradlew clean check --warning-mode=fail
```

Unit tests and PostgreSQL integration tests can also be run separately:

```bash
./gradlew test
./gradlew integrationTest
./gradlew e2eTest
```

The pull-request build requires at least 85% line coverage and 40% branch
coverage. Executable test classes must use the `*UnitTest`, `*IntegrationTest`,
or `*E2ETest` suffix. All CI jobs use GitHub-hosted `ubuntu-latest` runners.

## Run

The API requires database credentials at startup:

| Environment variable | Required | Default | Purpose |
| --- | --- | --- | --- |
| `API_DB_HOST` | yes | — | PostgreSQL host and port, such as `localhost:5432` |
| `API_DB_USERNAME` | yes | — | Prefer a read-only database user |
| `API_DB_PASSWORD` | yes | — | Password for the database user |
| `API_DB_DATABASE` | no | `discogs` | Database name |
| `API_SERVER_URL` | no | `http://localhost:8080` | Absolute public base URL used in API links |
| `SERVER_PORT` | no | `8080` | Application port |
| `MANAGEMENT_SERVER_PORT` | no | `8081` | Health and metrics port |

For example:

```bash
API_DB_HOST=localhost:5432 \
API_DB_USERNAME=discogs_reader \
API_DB_PASSWORD='<database-password>' \
./gradlew bootRun
```

OpenAPI documentation is available at `/swagger-ui.html`. Actuator health and
Prometheus metrics are served from the management port.

The primary resources are:

```text
/artists
/labels
/masters
/releases
```

## Releases

Conventional commits merged into `main` are collected by Release Please.
Merging its `build: release <version>` pull request creates the immutable tag
and GitHub Release. Only that release commit is allowed to build and publish
the `ghcr.io/dsub-io/open-discogs-api` container image; ordinary pushes and
pull requests never publish a production image.

## Contributing

Pull request titles and commit subjects must follow
[Conventional Commits](https://www.conventionalcommits.org/), for example:

```text
feat: add release date filters
fix(labels): return the configured resource URL
build: align the OpenDiscogs dependency set
```

Allowed types are `build`, `chore`, `ci`, `docs`, `feat`, `fix`, `perf`,
`refactor`, `revert`, `style`, and `test`. Pull-request branches must not use
the reserved `agent/`, `codex/`, or `claude/` prefixes.

## License

Licensed under the MIT License. Redistribution must retain the copyright and
permission notice in `LICENSE`.
