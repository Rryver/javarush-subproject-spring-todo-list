FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . /app/.
RUN mvn clean package -D maven.test.skip=true

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/*.jar
COPY --from=builder /app/target/libs/. /app/libs/.
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app/*.jar"]