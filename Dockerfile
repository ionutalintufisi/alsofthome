FROM eclipse-temurin:17-jdk

WORKDIR be/app

COPY be/gradlew .
COPY be/gradle/ gradle/
COPY be/build.gradle .
COPY be/settings.gradle .
COPY be/src/ src/

RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test

EXPOSE 8080
CMD ["java", "-jar", "build/libs/alsofthome-0.0.1-SNAPSHOT.jar"]
