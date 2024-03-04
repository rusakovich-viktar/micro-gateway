FROM openjdk:17-alpine3.14
COPY /build/libs/gateway-0.0.1.jar /micro-gate.jar
CMD ["java", "-jar", "/micro-gate.jar"]