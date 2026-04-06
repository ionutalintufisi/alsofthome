# Folosim imagine oficiala OpenJDK
FROM openjdk:17-jdk-slim

# Setăm directorul de lucru
WORKDIR /app

# Copiem fișierele necesare pentru build
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

# Copiem tot codul sursă
COPY src/ src/

# Permisiuni pentru gradlew
RUN chmod +x ./gradlew

# Construim aplicația și ignorăm testele
RUN ./gradlew bootJar -x test

# Expunem portul (Render setează variabila PORT)
EXPOSE 8080

# Pornim aplicația
CMD ["java", "-jar", "build/libs/myapp.jar"]
