# Changelog

## [1.3.0](https://github.com/sweatboys/open-discogs-api/compare/v1.2.2...v1.3.0) (2022-12-21)


### Features

* adds extra fields for helm values ([ee4a5ef](https://github.com/sweatboys/open-discogs-api/commit/ee4a5eff98c6b5b279f3173bafd62372f00dc93e))

## [1.2.2](https://github.com/sweatboys/open-discogs-api/compare/v1.2.1...v1.2.2) (2022-12-21)


### Bug Fixes

* corrects version update to be applied ([4da3ee5](https://github.com/sweatboys/open-discogs-api/commit/4da3ee5e6460e76e4b8b12cbe32d9f8bed18208c))

## [1.2.1](https://github.com/sweatboys/open-discogs-api/compare/v1.2.0...v1.2.1) (2022-12-21)


### Bug Fixes

* **helm:** adds quotation for ports ([266432a](https://github.com/sweatboys/open-discogs-api/commit/266432a69f65aaf8f4691cfa79b5af1b67ebfec8))

## [1.2.0](https://github.com/sweatboys/open-discogs-api/compare/v1.1.0...v1.2.0) (2022-12-21)


### Features

* routes actuator to API_SERVER_HOST target ([f8e61f4](https://github.com/sweatboys/open-discogs-api/commit/f8e61f4eb715d601412bfaed0b14874853bd1c00))

## [1.1.0](https://github.com/sweatboys/open-discogs-api/compare/v1.0.0...v1.1.0) (2022-12-21)


### Features

* adds support for open telemetry ([1997a08](https://github.com/sweatboys/open-discogs-api/commit/1997a085f546dba9551fb2a74cb5f077940fc7fd))


### Bug Fixes

* adds sortable params to artist controller ([0f26a30](https://github.com/sweatboys/open-discogs-api/commit/0f26a30445f6b643ee4ad6d9463b6ae9b8aa046e))
* updates container release strategy ([dad1e8f](https://github.com/sweatboys/open-discogs-api/commit/dad1e8f5effc5fa525f052e3eabce9b60ee29a19))

## 1.0.0 (2022-12-19)


### Features

* adds domain for label and master. ([e3670be](https://github.com/sweatboys/open-discogs-api/commit/e3670be18104db5883e666b2ffe6003692aaa779))
* adds example for query with r2dbc repository ([5082dc5](https://github.com/sweatboys/open-discogs-api/commit/5082dc514d385aad75bdd896d7d0b435cb7c577b))
* adds index on label full text searches ([581d38a](https://github.com/sweatboys/open-discogs-api/commit/581d38ae6452293cc9f142298e7d7a8b1214d3dd))
* adds index on label full text searches ([d7be0ff](https://github.com/sweatboys/open-discogs-api/commit/d7be0ff296db074c29a779e6333f3ac915fafecb))
* adds trigram text search on artist ([87d83d5](https://github.com/sweatboys/open-discogs-api/commit/87d83d5c9d9953803b4952617dcee6e61bbb0313))
* implements initial releases endpoint ([498d1c1](https://github.com/sweatboys/open-discogs-api/commit/498d1c18d78418a0be6de9de6d40c002d0648ce3))
* implements search on master ([78ceebd](https://github.com/sweatboys/open-discogs-api/commit/78ceebdd78e27f1aa9662e516f9947d5f12ee5b5))
* implements search on master ([f287bc9](https://github.com/sweatboys/open-discogs-api/commit/f287bc9de4ccfefde64995232c9a5f72fbba2011))
* implements search on master ([ed57ee7](https://github.com/sweatboys/open-discogs-api/commit/ed57ee77c851a5e8b1c09c9c9e8dce906293bc2c))
* initial implementation of artist service layer ([41151e3](https://github.com/sweatboys/open-discogs-api/commit/41151e3328a64ca3340b2fffb07bee00b538241f))
* initial implementation of label service layer ([1802801](https://github.com/sweatboys/open-discogs-api/commit/18028018aa6c54df6d30b5c13c3c34347959a548))
* initial implementation of label service layer ([8516cd1](https://github.com/sweatboys/open-discogs-api/commit/8516cd1a69999b634c809207cbb5e3bafe4ebb76))
* initial implementation of label service layer ([d8492f9](https://github.com/sweatboys/open-discogs-api/commit/d8492f9df6ca1533e15108a023278e75550d051a))
* initial implementation of label service layer ([b4731ee](https://github.com/sweatboys/open-discogs-api/commit/b4731ee4bd2bfafd77f9712adc6bf331c681137f))
* migrated and fixed artist releases api logic to jooq ([320ce21](https://github.com/sweatboys/open-discogs-api/commit/320ce2150ae185fb6b015e3c6a894b0a5468cecc))


### Bug Fixes

* adds indentation fix and error handling on API ([b95390a](https://github.com/sweatboys/open-discogs-api/commit/b95390a093a61efc62f3fd9a346c2a52d6e665ba))
* pluralized resource endpoint. ([9f361d3](https://github.com/sweatboys/open-discogs-api/commit/9f361d33422f6ddff676b98e49640c091cd7b37e))
* rebuild error handling with base exception ([dcc7fa1](https://github.com/sweatboys/open-discogs-api/commit/dcc7fa1af29fcba7b12bd2686dc41f06ee584e17))
* removes spring native layer on build ([b6c508e](https://github.com/sweatboys/open-discogs-api/commit/b6c508e6136895bbf3f7e253f240276e34b87d8f))
* unifies query param name for artist. ([4f5d378](https://github.com/sweatboys/open-discogs-api/commit/4f5d378fe695b6fc04719a31e434b72bc7f5b36c))
