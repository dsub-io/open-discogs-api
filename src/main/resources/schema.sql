CREATE TABLE "data" (
                        "etag" VARCHAR(200) UNIQUE PRIMARY KEY NOT NULL,
                        "generated_at" TIMESTAMP NOT NULL,
                        "checksum" VARCHAR(200) NOT NULL,
                        "target_type" VARCHAR(20) NOT NULL,
                        "uri" VARCHAR(2048) NOT NULL
);

CREATE TABLE "style" (
                         "id" SERIAL PRIMARY KEY NOT NULL,
                         "name" VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE "genre" (
                         "id" SERIAL PRIMARY KEY NOT NULL,
                         "name" VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE "artist" (
                          "id" INTEGER PRIMARY KEY NOT NULL,
                          "data_quality" VARCHAR(100),
                          "name" VARCHAR(1000),
                          "profile" TEXT,
                          "real_name" VARCHAR(2000)
);

CREATE TABLE "label" (
                         "id" INTEGER PRIMARY KEY NOT NULL,
                         "contact_info" TEXT,
                         "data_quality" VARCHAR(100),
                         "name" VARCHAR(300),
                         "profile" TEXT,
                         "parent_id" INTEGER
);

CREATE TABLE "master" (
                          "id" INTEGER PRIMARY KEY NOT NULL,
                          "data_quality" VARCHAR(100),
                          "title" VARCHAR(2000),
                          "released_year" SMALLINT
);

CREATE TABLE "release" (
                           "id" INTEGER PRIMARY KEY NOT NULL,
                           "title" VARCHAR(10000),
                           "country" VARCHAR(100),
                           "data_quality" VARCHAR(100),
                           "released_year" SMALLINT,
                           "released_month" SMALLINT,
                           "released_day" SMALLINT,
                           "listed_release_date" VARCHAR(255),
                           "master_id" INTEGER,
                           "is_master" BOOLEAN,
                           "notes" TEXT,
                           "status" VARCHAR(255)
);

CREATE TABLE "release_genre" (
                                 "release_id" INTEGER NOT NULL,
                                 "genre_id" INTEGER NOT NULL,
                                 "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                 PRIMARY KEY ("release_id", "genre_id")
);

CREATE TABLE "release_track" (
                                 "release_id" INTEGER NOT NULL,
                                 "duration" VARCHAR(1500),
                                 "position" VARCHAR(1500),
                                 "title" VARCHAR(10000),
                                 "title_hash" BIGINT NOT NULL,
                                 "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                 PRIMARY KEY ("release_id", "title_hash")
);

CREATE TABLE "label_release" (
                                 "label_id" INTEGER NOT NULL,
                                 "release_id" INTEGER NOT NULL,
                                 "category_notation" VARCHAR(1000),
                                 "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                 PRIMARY KEY ("label_id", "release_id")
);

CREATE TABLE "release_image" (
                                 "release_id" INTEGER NOT NULL,
                                 "url_hash" BIGINT NOT NULL,
                                 "url" VARCHAR(2048) NOT NULL,
                                 "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                 PRIMARY KEY ("release_id", "url_hash")
);

CREATE TABLE "release_contract" (
                                    "release_id" INTEGER NOT NULL,
                                    "label_id" INTEGER NOT NULL,
                                    "contract_hash" BIGINT NOT NULL,
                                    "contract" VARCHAR(5000) NOT NULL,
                                    "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                    PRIMARY KEY ("release_id", "label_id", "contract_hash")
);

CREATE TABLE "release_identifier" (
                                      "release_id" INTEGER NOT NULL,
                                      "description" TEXT,
                                      "type" VARCHAR(2500),
                                      "value" TEXT,
                                      "identifier_hash" BIGINT NOT NULL,
                                      "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                      PRIMARY KEY ("release_id", "identifier_hash")
);

CREATE TABLE "master_track" (
                                "master_id" INTEGER NOT NULL,
                                "duration" VARCHAR(1500),
                                "position" VARCHAR(1500),
                                "title" VARCHAR(10000),
                                "title_hash" BIGINT NOT NULL,
                                "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                PRIMARY KEY ("master_id", "title_hash")
);

CREATE TABLE "master_video" (
                                "master_id" INTEGER NOT NULL,
                                "url_hash" BIGINT NOT NULL,
                                "url" VARCHAR(2048) NOT NULL,
                                "description" VARCHAR(4000),
                                "title" VARCHAR(1000),
                                "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                PRIMARY KEY ("master_id", "url_hash")
);

CREATE TABLE "master_genre" (
                                "master_id" INTEGER NOT NULL,
                                "genre_id" INTEGER NOT NULL,
                                "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                PRIMARY KEY ("master_id", "genre_id")
);

CREATE TABLE "master_style" (
                                "master_id" INTEGER NOT NULL,
                                "style_id" INTEGER NOT NULL,
                                "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                PRIMARY KEY ("master_id", "style_id")
);

CREATE TABLE "release_style" (
                                 "release_id" INTEGER NOT NULL,
                                 "style_id" INTEGER NOT NULL,
                                 "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                 PRIMARY KEY ("release_id", "style_id")
);

CREATE TABLE "release_video" (
                                 "release_id" INTEGER NOT NULL,
                                 "description" VARCHAR(4000),
                                 "title" VARCHAR(1000),
                                 "url" VARCHAR(2048) NOT NULL,
                                 "url_hash" BIGINT NOT NULL,
                                 "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                 PRIMARY KEY ("release_id", "url_hash")
);

CREATE TABLE "label_url" (
                             "label_id" INTEGER NOT NULL,
                             "url_hash" BIGINT NOT NULL,
                             "url" VARCHAR(2048) NOT NULL,
                             "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                             PRIMARY KEY ("label_id", "url_hash")
);

CREATE TABLE "release_format" (
                                  "release_id" INTEGER NOT NULL,
                                  "description" VARCHAR(10000),
                                  "name" VARCHAR(255),
                                  "quantity" INTEGER,
                                  "text" VARCHAR(5000),
                                  "format_hash" BIGINT NOT NULL,
                                  "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                  PRIMARY KEY ("release_id", "format_hash")
);

CREATE TABLE "artist_alias" (
                                "artist_id" INTEGER NOT NULL,
                                "alias_id" INTEGER NOT NULL,
                                "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                PRIMARY KEY ("artist_id", "alias_id")
);

CREATE TABLE "artist_name_variation" (
                                         "artist_id" INTEGER NOT NULL,
                                         "name_variation" VARCHAR(2000) NOT NULL,
                                         "name_variation_hash" BIGINT NOT NULL,
                                         "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                         PRIMARY KEY ("artist_id", "name_variation_hash")
);

CREATE TABLE "master_artist" (
                                 "artist_id" INTEGER NOT NULL,
                                 "master_id" INTEGER NOT NULL,
                                 "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                 PRIMARY KEY ("artist_id", "master_id")
);

CREATE TABLE "release_artist" (
                                  "release_id" INTEGER NOT NULL,
                                  "artist_id" INTEGER NOT NULL,
                                  "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                  PRIMARY KEY ("release_id", "artist_id")
);

CREATE TABLE "release_credited_artist" (
                                           "release_id" INTEGER NOT NULL,
                                           "artist_id" INTEGER NOT NULL,
                                           "role_hash" BIGINT NOT NULL,
                                           "role" VARCHAR(10000),
                                           "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                           PRIMARY KEY ("release_id", "artist_id", "role_hash")
);

CREATE TABLE "artist_url" (
                              "artist_id" INTEGER NOT NULL,
                              "url_hash" BIGINT NOT NULL,
                              "url" VARCHAR(2048) NOT NULL,
                              "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                              PRIMARY KEY ("artist_id", "url_hash")
);

CREATE TABLE "artist_group" (
                                "artist_id" INTEGER NOT NULL,
                                "group_id" INTEGER NOT NULL,
                                "updated_at" TIMESTAMP NOT NULL DEFAULT (NOW()),
                                PRIMARY KEY ("artist_id", "group_id")
);

CREATE INDEX "pk_style" ON "style" ("id");

COMMENT ON TABLE "data" IS 'Cached resource for keep tracking data dump updates (either being monthly or random occasions)';

COMMENT ON COLUMN "data"."etag" IS 'ETag representing this data being unique. Used for updating cache.';

COMMENT ON COLUMN "data"."generated_at" IS 'Date this data is generated at.';

COMMENT ON COLUMN "data"."checksum" IS 'Checksum to validate when fetching dump source.';

COMMENT ON COLUMN "data"."target_type" IS 'Type of data. Referred as artists, labels, masters, release.
Always uppercase.';

COMMENT ON COLUMN "data"."uri" IS 'URI to download dump data file.';

COMMENT ON COLUMN "style"."id" IS 'id of style';

COMMENT ON COLUMN "style"."name" IS 'name of style';

COMMENT ON COLUMN "genre"."id" IS 'id of genre';

COMMENT ON COLUMN "genre"."name" IS 'name of genre';

COMMENT ON COLUMN "release"."master_id" IS 'id of master release this release belongs to';

COMMENT ON COLUMN "release"."is_master" IS 'indicates i this release is main release of the master release';

COMMENT ON COLUMN "release_genre"."updated_at" IS 'created time';

COMMENT ON COLUMN "release_track"."title_hash" IS 'fnv32 encoded hash from title';

COMMENT ON COLUMN "release_track"."updated_at" IS 'created time';

COMMENT ON COLUMN "label_release"."updated_at" IS 'created time';

COMMENT ON COLUMN "release_image"."url_hash" IS 'fnv32 encoded hash from url';

COMMENT ON COLUMN "release_image"."updated_at" IS 'created time';

COMMENT ON COLUMN "release_contract"."contract_hash" IS 'fnv32 encoded hash from contract';

COMMENT ON COLUMN "release_contract"."updated_at" IS 'created time';

COMMENT ON COLUMN "release_identifier"."identifier_hash" IS 'fnv32 encoded hash from string which is description, type, value appended in order';

COMMENT ON COLUMN "release_identifier"."updated_at" IS 'created time';

COMMENT ON COLUMN "master_track"."title_hash" IS 'fnv32 encoded hash from title';

COMMENT ON COLUMN "master_track"."updated_at" IS 'created time';

COMMENT ON COLUMN "master_video"."url_hash" IS 'fnv32 encoded hash from url';

COMMENT ON COLUMN "master_video"."updated_at" IS 'created time';

COMMENT ON COLUMN "master_genre"."updated_at" IS 'created time';

COMMENT ON COLUMN "master_style"."updated_at" IS 'created time';

COMMENT ON COLUMN "release_style"."updated_at" IS 'created time';

COMMENT ON COLUMN "release_video"."url_hash" IS 'fnv32 encoded hash from url';

COMMENT ON COLUMN "release_video"."updated_at" IS 'created time';

COMMENT ON COLUMN "label_url"."url_hash" IS 'fnv32 encoded hash from url';

COMMENT ON COLUMN "label_url"."updated_at" IS 'created time';

COMMENT ON COLUMN "release_format"."format_hash" IS 'fnv32 encoded hash from string which is description, name, quantity, text appended in order';

COMMENT ON COLUMN "release_format"."updated_at" IS 'created time';

COMMENT ON COLUMN "artist_alias"."updated_at" IS 'created time';

COMMENT ON COLUMN "artist_name_variation"."artist_id" IS 'id of artist';

COMMENT ON COLUMN "artist_name_variation"."name_variation" IS 'artist"s other name';

COMMENT ON COLUMN "artist_name_variation"."name_variation_hash" IS 'fnv32 encoded hash from name_variation';

COMMENT ON COLUMN "artist_name_variation"."updated_at" IS 'created time';

COMMENT ON COLUMN "master_artist"."artist_id" IS 'artist id of the master release';

COMMENT ON COLUMN "master_artist"."master_id" IS 'master id';

COMMENT ON COLUMN "master_artist"."updated_at" IS 'created time';

COMMENT ON COLUMN "release_artist"."updated_at" IS 'created time';

COMMENT ON COLUMN "release_credited_artist"."role_hash" IS 'fnv32 encoded hash from role';

COMMENT ON COLUMN "release_credited_artist"."role" IS 'role of an artist for a release';

COMMENT ON COLUMN "release_credited_artist"."updated_at" IS 'created time';

COMMENT ON COLUMN "artist_url"."url_hash" IS 'fnv32 encoded hash from url';

COMMENT ON COLUMN "artist_url"."updated_at" IS 'created time';

COMMENT ON COLUMN "artist_group"."updated_at" IS 'created time';

ALTER TABLE "label" ADD CONSTRAINT "fk_label_parent_id_label_id" FOREIGN KEY ("parent_id") REFERENCES "label" ("id");

ALTER TABLE "release_genre" ADD CONSTRAINT "fk_release_genre_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "release_genre" ADD CONSTRAINT "fk_release_genre_genre_id_genre" FOREIGN KEY ("genre_id") REFERENCES "genre" ("id");

ALTER TABLE "release_track" ADD CONSTRAINT "fk_release_track_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "label_release" ADD CONSTRAINT "fk_label_release_label_id_label" FOREIGN KEY ("label_id") REFERENCES "label" ("id");

ALTER TABLE "label_release" ADD CONSTRAINT "fk_label_release_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "release_image" ADD CONSTRAINT "fk_release_image_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "release_contract" ADD CONSTRAINT "fk_release_contract_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "release_contract" ADD CONSTRAINT "fk_release_contract_label_id_label" FOREIGN KEY ("label_id") REFERENCES "label" ("id");

ALTER TABLE "release_identifier" ADD CONSTRAINT "fk_release_identifier_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "master_video" ADD CONSTRAINT "fk_master_video_master_id_master" FOREIGN KEY ("master_id") REFERENCES "master" ("id");

ALTER TABLE "master_genre" ADD CONSTRAINT "fk_master_genre_master_id_master" FOREIGN KEY ("master_id") REFERENCES "master" ("id");

ALTER TABLE "master_genre" ADD CONSTRAINT "fk_master_genre_genre_id_genre" FOREIGN KEY ("genre_id") REFERENCES "genre" ("id");

ALTER TABLE "master_style" ADD CONSTRAINT "fk_master_style_master_id_master" FOREIGN KEY ("master_id") REFERENCES "master" ("id");

ALTER TABLE "master_style" ADD CONSTRAINT "fk_master_style_style_id_style" FOREIGN KEY ("style_id") REFERENCES "style" ("id");

ALTER TABLE "release_style" ADD CONSTRAINT "fk_release_style_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "release_style" ADD CONSTRAINT "fk_release_style_style_id_style" FOREIGN KEY ("style_id") REFERENCES "style" ("id");

ALTER TABLE "release_video" ADD CONSTRAINT "fk_release_video_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "label_url" ADD CONSTRAINT "fk_label_url_label_id_label" FOREIGN KEY ("label_id") REFERENCES "label" ("id");

ALTER TABLE "release_format" ADD CONSTRAINT "fk_release_format_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "artist_alias" ADD CONSTRAINT "fk_artist_alias_artist_id_artist" FOREIGN KEY ("artist_id") REFERENCES "artist" ("id");

ALTER TABLE "artist_alias" ADD CONSTRAINT "fk_artist_alias_alias_id_artist" FOREIGN KEY ("alias_id") REFERENCES "artist" ("id");

ALTER TABLE "artist_name_variation" ADD CONSTRAINT "fk_artist_name_variation_artist_id_artist" FOREIGN KEY ("artist_id") REFERENCES "artist" ("id");

ALTER TABLE "master_artist" ADD CONSTRAINT "fk_master_artist_artist_id_artist" FOREIGN KEY ("artist_id") REFERENCES "artist" ("id");

ALTER TABLE "master_artist" ADD CONSTRAINT "fk_master_artist_master_id_master" FOREIGN KEY ("master_id") REFERENCES "master" ("id");

ALTER TABLE "release_artist" ADD CONSTRAINT "fk_release_artist_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "release_artist" ADD CONSTRAINT "fk_release_artist_artist_id_artist" FOREIGN KEY ("artist_id") REFERENCES "artist" ("id");

ALTER TABLE "release_credited_artist" ADD CONSTRAINT "fk_release_credited_artist_release_id_release" FOREIGN KEY ("release_id") REFERENCES "release" ("id");

ALTER TABLE "release_credited_artist" ADD CONSTRAINT "fk_release_credited_artist_artist_id_artist" FOREIGN KEY ("artist_id") REFERENCES "artist" ("id");

ALTER TABLE "artist_url" ADD CONSTRAINT "fk_artist_url_artist_id_artist" FOREIGN KEY ("artist_id") REFERENCES "artist" ("id");

ALTER TABLE "artist_group" ADD CONSTRAINT "fk_artist_group_artist_id_artist" FOREIGN KEY ("artist_id") REFERENCES "artist" ("id");

ALTER TABLE "artist_group" ADD CONSTRAINT "fk_artist_group_group_id_artist" FOREIGN KEY ("group_id") REFERENCES "artist" ("id");

ALTER TABLE "release" ADD CONSTRAINT "fk_release_master_id_master" FOREIGN KEY ("master_id") REFERENCES "master" ("id");

-- CREATE EXTENSION FOR TRIGRAM TOKENIZER
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- INDEXES FOR RELATIONAL LOOKUPS
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_artist_group_group_id_artist_id ON artist_group(group_id);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_release_artist_artist_id_release_id ON release_artist(artist_id);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_release_credited_artist_artist_id_release_id ON release_credited_artist(artist_id);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_master_year ON master(released_year);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_release_master_id ON release(master_id);

-- INDEXES FOR FULL TEXT SEARCH
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_artist_name_trigram ON artist USING GIN(UPPER("name") gin_trgm_ops);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_artist_real_name_trigram ON artist USING GIN(UPPER("real_name") gin_trgm_ops);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_artist_profile_trigram ON artist USING GIN(UPPER("profile") gin_trgm_ops);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_label_name_trigram ON label USING GIN(UPPER("name") gin_trgm_ops);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_label_profile_trigram ON label USING GIN(UPPER("profile") gin_trgm_ops);
CREATE INDEX CONCURRENTLY IF NOT EXISTS idx_master_title_trigram ON master USING GIN(UPPER("title") gin_trgm_ops);
