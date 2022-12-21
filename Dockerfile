FROM curlimages/curl:7.86.0 AS download
ARG OTEL_AGENT_VERSION="1.21.0"
RUN curl --silent --fail -L "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_AGENT_VERSION}/opentelemetry-javaagent.jar" \
    -o "$HOME/opentelemetry-javaagent.jar"

FROM gradle:jdk19-alpine AS build
ADD . /
RUN cd / && gradle bootJar --quiet

FROM eclipse-temurin:19-alpine
COPY --from=build /build/libs/*.jar /app.jar
COPY --from=download /home/curl_user/opentelemetry-javaagent.jar /opentelemetry-javaagent.jar

ENTRYPOINT java -jar -javaagent:/opentelemetry-javaagent.jar /app.jar