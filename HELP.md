# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.0/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.0/gradle-plugin/reference/html/#build-image)
* [GraalVM Native Image Support](https://docs.spring.io/spring-boot/docs/3.0.0/reference/html/native-image.html#native-image)
* [Testcontainers R2DBC support Reference Guide](https://www.testcontainers.org/modules/databases/r2dbc/)
* [Testcontainers Postgres Module Reference Guide](https://www.testcontainers.org/modules/databases/postgres/)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#appendix.configuration-metadata.annotation-processor)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#web.reactive)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#actuator)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/3.0.0/reference/htmlsingle/#data.sql.r2dbc)
* [Testcontainers](https://www.testcontainers.org/)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Accessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [Configure AOT settings in Build Plugin](https://docs.spring.io/spring-boot/docs/3.0.0/gradle-plugin/reference/htmlsingle/#aot)
* [R2DBC Homepage](https://r2dbc.io)

## GraalVM Native Support

This project has been configured to let you generate either a lightweight container or a native
executable.
It is also possible to run your tests in a native image.

### Lightweight Container with Cloud Native Buildpacks

If you're already familiar with Spring Boot container images support, this is the easiest way to get
started.
Docker should be installed and configured on your machine prior to creating the image.

To create the image, run the following goal:

```
$ ./gradlew bootBuildImage
```

Then, you can run the app like any other container:

```
$ docker run --rm open-discogs-api:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools

Use this option if you want to explore more options such as running your tests in a native image.
The GraalVM `native-image` compiler should be installed and configured on your machine.

NOTE: GraalVM 22.3+ is required.

To create the executable, run the following goal:

```
$ ./gradlew nativeCompile
```

Then, you can run the app as follows:

```
$ build/native/nativeCompile/open-discogs-api
```

You can also run your existing tests suite in a native image.
This is an efficient way to validate the compatibility of your application.

To run your existing tests in a native image, run the following goal:

```
$ ./gradlew nativeTest
```

