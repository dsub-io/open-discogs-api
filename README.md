# OpenDiscogs API

A read-only, reactive HTTP API for PostgreSQL databases populated by
[OpenDiscogs Batch](https://github.com/dsub-io/open-discogs-batch). It exposes
artists, labels, masters, and releases imported from the public monthly data
dumps.

This is an independent DSUB project. It is not affiliated with or endorsed by
Discogs. The Discogs name is used only to identify the public data source.

[![Build](https://github.com/dsub-io/open-discogs-api/actions/workflows/build.yml/badge.svg)](https://github.com/dsub-io/open-discogs-api/actions/workflows/build.yml)
[![CodeQL](https://github.com/dsub-io/open-discogs-api/actions/workflows/codeql.yml/badge.svg)](https://github.com/dsub-io/open-discogs-api/actions/workflows/codeql.yml)
[![Release](https://github.com/dsub-io/open-discogs-api/actions/workflows/release.yml/badge.svg)](https://github.com/dsub-io/open-discogs-api/actions/workflows/release.yml)

<!-- x-release-please-start-version -->
Current version: `1.6.2`
<!-- x-release-please-end -->

## OpenDiscogs stack

The OpenDiscogs repositories have separate responsibilities:

- [open-discogs-batch](https://github.com/dsub-io/open-discogs-batch) downloads,
  verifies, and imports the public monthly data dumps into PostgreSQL.
- [open-discogs-jooq](https://github.com/dsub-io/open-discogs-jooq) publishes
  the generated PostgreSQL schema model to Maven Central.
- `open-discogs-api` consumes that Maven artifact and serves the imported data
  through Spring WebFlux and R2DBC.

The API currently consumes:

```text
io.dsub.opendiscogs:open-discogs-jooq:0.0.5
```

Generated jOOQ sources and package-registry credentials do not belong in this
repository.

## Run the container

The public container is available from the
[open-discogs-api package](https://github.com/dsub-io/open-discogs-api/pkgs/container/open-discogs-api).
A PostgreSQL database initialized and populated by OpenDiscogs Batch must
already be reachable.

Pull and run a versioned image:

<!-- x-release-please-start-version -->
```bash
docker pull ghcr.io/dsub-io/open-discogs-api:1.6.2

docker run --rm --name open-discogs-api \
  --publish 8080:8080 \
  --publish 8081:8081 \
  --env API_DB_HOST=host.docker.internal:5432 \
  --env API_DB_DATABASE=discogs \
  --env API_DB_USERNAME=discogs_reader \
  --env API_DB_PASSWORD='<database-password>' \
  --env API_SERVER_URL=http://localhost:8080 \
  ghcr.io/dsub-io/open-discogs-api:1.6.2
```
<!-- x-release-please-end -->

The example uses Docker Desktop's `host.docker.internal` address for a database
running on the host. On Linux, add
`--add-host=host.docker.internal:host-gateway`, or set `API_DB_HOST` to a
database hostname reachable from the container. Prefer a read-only database
role.

Check the running service from another terminal:

```bash
curl --fail http://localhost:8081/actuator/health
curl --fail http://localhost:8080/v3/api-docs
```

Interactive OpenAPI documentation is available at
`http://localhost:8080/swagger-ui.html`. The management port also exposes
`/actuator/info` and `/actuator/prometheus`; protect that port from untrusted
networks.

Published images target `linux/amd64` and include SBOM and provenance
attestations. Version tags (`MAJOR.MINOR.PATCH` and `vMAJOR.MINOR.PATCH`)
identify a release, `sha-<release-commit>` identifies its source commit, and
`latest` moves to the newest release. Use an image digest when deployment
immutability is required.

## Configuration

The application validates its database settings during startup.

| Environment variable | Required | Default | Purpose |
| --- | --- | --- | --- |
| `API_DB_HOST` | yes | — | PostgreSQL host and port, such as `postgres:5432` |
| `API_DB_USERNAME` | yes | — | Database user; a read-only role is recommended |
| `API_DB_PASSWORD` | yes | — | Password for the database user |
| `API_DB_DATABASE` | no | `discogs` | Database name |
| `API_SERVER_URL` | no | `http://localhost:8080` | OpenAPI server URL |
| `SERVER_PORT` | no | `8080` | Public API port |
| `MANAGEMENT_SERVER_PORT` | no | `8081` | Actuator port |

## API

All endpoints are read-only. Collection endpoints accept `page`, `size`, and
resource-specific filters; page numbers start at 1 and page size is capped at
30. Supported filters and sort fields are described in the generated OpenAPI
document.

| Resource | Collection and detail endpoints | Related releases |
| --- | --- | --- |
| Artists | `/artists`, `/artists/{id}` | `/artists/{id}/releases` |
| Labels | `/labels`, `/labels/{id}` | `/labels/{id}/releases` |
| Masters | `/masters`, `/masters/{id}` | `/masters/{id}/releases` |
| Releases | `/releases`, `/releases/{id}` | — |

For example:

```bash
curl --get http://localhost:8080/releases \
  --data-urlencode 'title=Kind of Blue' \
  --data 'page=1' \
  --data 'size=10'
```

## Develop locally

Requirements:

- JDK 21
- Docker, used by PostgreSQL integration and E2E tests
- a PostgreSQL database initialized by the current OpenDiscogs schema to run
  the application against real imported data

The Gradle wrapper uses Gradle 9.6.1. The application uses Spring Boot 4.1,
jOOQ 3.21.6, and Testcontainers 2.0.5. With SDKMAN installed, activate the
repository toolchain before running Gradle:

```bash
sdk env
```

Run the complete deterministic suite and coverage gate:

```bash
./gradlew clean check --no-daemon --warning-mode=fail
```

Individual test boundaries are also available:

```bash
./gradlew test
./gradlew integrationTest
./gradlew e2eTest
```

The build enforces at least 85% line coverage and 40% branch coverage.
Executable test classes must end in `UnitTest`, `IntegrationTest`, or
`E2ETest`. Pull-request CI runs the suite, builds the production container,
reviews dependency changes, validates Conventional Commits, and analyzes Java
with CodeQL on GitHub-hosted `ubuntu-latest` runners.

Run from source with a populated database:

```bash
API_DB_HOST=localhost:5432 \
API_DB_USERNAME=discogs_reader \
API_DB_PASSWORD='<database-password>' \
./gradlew bootRun
```

## Releases

Release Please collects Conventional Commits from `main`:

- `fix:` produces a patch candidate.
- `feat:` produces a minor candidate.
- `!` or a `BREAKING CHANGE:` footer produces a major candidate.
- `docs:` by itself does not create or update a release pull request.

Merging the generated `build: release <version>` pull request creates the tag
and GitHub Release. The release workflow rebuilds that exact release commit,
runs the full test and coverage suite, and only then publishes the GHCR image.
Ordinary pushes and pull requests build containers for verification but never
publish production images.

## Contributing

Pull request titles and commit subjects must follow
[Conventional Commits](https://www.conventionalcommits.org/), for example:

```text
feat: add release date filters
fix(labels): return the configured resource URL
docs: clarify container networking
build: align the OpenDiscogs dependency set
```

Allowed types are `build`, `chore`, `ci`, `docs`, `feat`, `fix`, `perf`,
`refactor`, `revert`, `style`, and `test`. Pull-request branches must not use
the reserved `agent/`, `codex/`, or `claude/` prefixes.

## License

Licensed under the [MIT License](LICENSE). Redistribution must retain the
copyright and permission notice.
