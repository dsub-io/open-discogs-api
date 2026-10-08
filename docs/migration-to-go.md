# Migrating from the Java API to Go

The Java API is deprecated. New deployments should use
[Go OpenDiscogs API](https://github.com/dsub-io/go-open-discogs-api) with
[Go OpenDiscogs Batch](https://github.com/dsub-io/go-open-discogs-batch).

The Go service requires client and database preparation. Changing the container
image alone is not a supported migration.

## Database preparation

The Java API currently depends on `open-discogs-jooq:0.0.5`. The Go services use
the canonical `open-discogs-model` module and its migration ledger. Do not assume
that a database serving this Java API satisfies the Go schema contract.

1. Back up the database and record the current importer, schema, and versions.
2. Stop all importers before applying canonical migrations. Follow the model's
   [schema ownership and compatibility rules](https://github.com/dsub-io/open-discogs-model#schema-ownership)
   and the Go Batch [import safety guide](https://github.com/dsub-io/go-open-discogs-batch/blob/main/docs/import-safety.md).
   Legacy Liquibase adoption accepts only the histories documented by the model.
3. Use an importer that bundles the migrations required by the target API.
   The current Go API requires V023 for exact Release lookups. Check the target
   release's README when selecting versions; an older importer rejects a ledger
   newer than its bundled model.
4. Configure the API's read-only role and `API_DATABASE_SCHEMA` for the imported
   schema. The Go API validates its tables and privileges; it never migrates them.

## Client changes

| Contract | Java API | Go API |
| --- | --- | --- |
| Resource collections | One-based `page`, configurable sort fields | Ascending resource ID; `after_id`, `next_after_id`, `has_more` |
| Page size | At most 30 | Default 20, at most 30 |
| Totals | Page-based response | No exact totals or page counts |
| Release tracks and identifiers | Consult Java OpenAPI | Separate bounded endpoints with opaque `cursor` / `next_cursor` |
| OpenAPI | `/v3/api-docs` | `/openapi.json`, with `/v3/api-docs` compatibility path |
| Management health | `/actuator/health` | `/healthz` and `/readyz`; `/actuator/health` aliases readiness |

Compare both OpenAPI documents against the endpoints and filters your client
uses. Update DTOs, pagination, sorting assumptions, and error handling before
switching traffic. The Go README documents search limits, exact lookup selectors,
and relation ordering.

## Deployment

Use the Go API's [configuration and container examples](https://github.com/dsub-io/go-open-discogs-api#run).
Public HTTP defaults to port `8080`; management defaults to loopback port `8081`.
Keep management endpoints private and supply database credentials through secrets.

Test representative client requests against a separate Go deployment before
switching traffic. `/snapshot` reports import completion; readiness only confirms
that the API can serve committed data. A partial import can therefore be ready
while some catalog records are still missing. Keep a rollback plan that accounts
for schema changes as well as the API image.
