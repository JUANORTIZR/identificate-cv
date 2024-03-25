FROM eclipse-temurin:17

LABEL autor=onek.code

RUN mvn clean package

