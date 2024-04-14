FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml ./
RUN mvn clean package

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
