FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .
COPY src/ src/

RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test

EXPOSE 8080
CMD ["java", "-jar", "build/libs/alsofthome-0.0.1-SNAPSHOT.jar"]
