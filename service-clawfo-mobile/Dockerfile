FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/contractlens-analyzer.jar app.jar

EXPOSE 9002

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-XX:InitialRAMPercentage=25.0", "-jar", "app.jar"]