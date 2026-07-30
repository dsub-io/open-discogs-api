FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /workspace
COPY gradle gradle
COPY gradlew build.gradle settings.gradle gradle.properties version.txt ./
RUN ./gradlew dependencies --no-daemon --warning-mode=fail
COPY src src
COPY LICENSE NOTICE ./
RUN ./gradlew clean bootJar --no-daemon --warning-mode=fail

FROM eclipse-temurin:21-jre-jammy

RUN groupadd --system open-discogs \
    && useradd --system --gid open-discogs --home-dir /app open-discogs
WORKDIR /app
COPY --from=build --chown=open-discogs:open-discogs /workspace/build/libs/*.jar open-discogs-api.jar

USER open-discogs
EXPOSE 8080 8081
ENTRYPOINT ["java", "-jar", "/app/open-discogs-api.jar"]
