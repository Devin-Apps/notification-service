# Build stage
FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Runtime stage
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/notification-service-1.0-SNAPSHOT.jar .
COPY --from=build /app/src/main/resources/config.yml .
RUN apt-get update && apt-get install -y curl && \
    addgroup --system appgroup && adduser --system --ingroup appgroup appuser && \
    chown -R appuser:appgroup /app
USER appuser
EXPOSE 8080
ENV JAVA_OPTS="-Xms256m -Xmx512m -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT ["java", "-jar", "notification-service-1.0-SNAPSHOT.jar", "server"]

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:8080/healthcheck || exit 1
