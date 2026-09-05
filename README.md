# OpenDiscogs API

[![Build](https://github.com/dsub-io/open-discogs-api/actions/workflows/build.yml/badge.svg)](https://github.com/dsub-io/open-discogs-api/actions/workflows/build.yml)
[![CodeQL](https://github.com/dsub-io/open-discogs-api/actions/workflows/codeql.yml/badge.svg)](https://github.com/dsub-io/open-discogs-api/actions/workflows/codeql.yml)
[![Release](https://github.com/dsub-io/open-discogs-api/actions/workflows/release.yml/badge.svg)](https://github.com/dsub-io/open-discogs-api/actions/workflows/release.yml)

A read-only HTTP API for artists, labels, masters, and releases imported from
the public Discogs monthly data dumps.

OpenDiscogs is an independent DSUB project. It is not affiliated with or
endorsed by Discogs.

<!-- x-release-please-start-version -->
Current version: `1.6.2`
<!-- x-release-please-end -->

## Quick start

The API requires a PostgreSQL database that has already been initialized and
populated by [OpenDiscogs Batch](https://github.com/dsub-io/open-discogs-batch).
Use a database role with read-only access.

Create an environment file:

```dotenv
API_DB_HOST=host.docker.internal:5432
API_DB_DATABASE=discogs
API_DB_USERNAME=discogs_reader
API_DB_PASSWORD=replace-me
API_SERVER_URL=http://localhost:8080
```

Pull and run the current release:

<!-- x-release-please-start-version -->
```bash
docker pull ghcr.io/dsub-io/open-discogs-api:1.6.2

docker run --rm --name open-discogs-api \
  --env-file .env \
  --publish 8080:8080 \
  --publish 127.0.0.1:8081:8081 \
  ghcr.io/dsub-io/open-discogs-api:1.6.2
```
<!-- x-release-please-end -->

On Linux, add `--add-host=host.docker.internal:host-gateway` when PostgreSQL
runs on the host. Otherwise set `API_DB_HOST` to a hostname reachable from
the container.

Verify the service:

```bash
curl --fail http://localhost:8081/actuator/health
curl --fail http://localhost:8080/v3/api-docs
```

The public API is available on port `8080`. Port `8081` serves Actuator
health, info, and Prometheus endpoints and should remain private.

## API

Interactive documentation is available at
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
after startup. The generated OpenAPI document is the authoritative endpoint
and parameter reference.

| Resource | Collection and detail endpoints | Related releases |
| --- | --- | --- |
| Artists | `/artists`, `/artists/{id}` | `/artists/{id}/releases` |
| Labels | `/labels`, `/labels/{id}` | `/labels/{id}/releases` |
| Masters | `/masters`, `/masters/{id}` | `/masters/{id}/releases` |
| Releases | `/releases`, `/releases/{id}` | — |

Collection endpoints use one-based `page` values, cap `size` at 30, and
support the filters and sort fields documented in OpenAPI.

```bash
curl --get http://localhost:8080/releases \
  --data-urlencode 'title=Kind of Blue' \
  --data 'page=1' \
  --data 'size=10'
```

## Configuration

The application validates its database settings during startup.

| Environment variable | Required | Default | Purpose |
| --- | --- | --- | --- |
| `API_DB_HOST` | yes | — | PostgreSQL host and port |
| `API_DB_USERNAME` | yes | — | PostgreSQL user |
| `API_DB_PASSWORD` | yes | — | PostgreSQL password |
| `API_DB_DATABASE` | no | `discogs` | PostgreSQL database |
| `API_SERVER_URL` | no | `http://localhost:8080` | Public URL shown in OpenAPI |
| `SERVER_PORT` | no | `8080` | Public API port |
| `MANAGEMENT_SERVER_PORT` | no | `8081` | Actuator port |

Keep credentials out of command-line arguments and source control. Published
images target `linux/amd64`; use a digest instead of a version tag when an
immutable deployment is required.

## Data source and usage

The service reads only the monthly dump snapshot already imported into
PostgreSQL. It does not call the Discogs API, accept Discogs credentials,
perform live hydration, or write catalog data. A `404` only means that a
resource is absent from the imported snapshot.

Applications that need fresher data must integrate the Discogs API separately
and comply with its current
[API Terms of Use](https://support.discogs.com/hc/en-us/articles/360009334593-API-Terms-of-Use),
including freshness, caching, attribution, rate-limit, Restricted Data,
availability, and termination requirements. This repository's MIT license
covers the source code, not third-party data.

## Repository roles

- [open-discogs-model](https://github.com/dsub-io/open-discogs-model) owns the
  canonical PostgreSQL schema and publishes the generated Java model.
- [open-discogs-batch](https://github.com/dsub-io/open-discogs-batch) imports
  verified public monthly dumps.
- `open-discogs-api` serves the populated database through Spring WebFlux and
  R2DBC.

The API currently consumes
`io.dsub.opendiscogs:open-discogs-jooq:0.0.5` from Maven Central. Generated
model sources and package credentials do not belong in this repository.

## Development

Requirements:

- JDK 21
- Docker for PostgreSQL integration and end-to-end tests

Use the checked-in Gradle wrapper:

```bash
./gradlew clean check --no-daemon --warning-mode=fail
```

The full check runs unit, PostgreSQL integration, HTTP end-to-end, test naming,
and coverage gates. Individual suites are available as `test`,
`integrationTest`, and `e2eTest`.

To run the application against an existing database:

```bash
API_DB_HOST=localhost:5432 \
API_DB_USERNAME=discogs_reader \
API_DB_PASSWORD=replace-me \
./gradlew bootRun
```

## Releases and contributions

Pull request titles and commit subjects use
[Conventional Commits](https://www.conventionalcommits.org/). For example:

```text
docs: clarify container networking
fix(labels): return the configured resource URL
feat: add release date filters
```

`fix:` creates a patch candidate, `feat:` creates a minor candidate, and a
breaking change creates a major candidate. A documentation-only `docs:`
change does not create or update a release pull request.

Merging a Release Please pull request creates the GitHub release and triggers
publication of the matching GHCR image after the full verification suite.

## License

Licensed under the [MIT License](LICENSE). Discogs data remains subject to the
rights and terms applicable at its source.
