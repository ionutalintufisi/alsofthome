# Folosește imagine oficială OpenJDK
FROM openjdk:17-jdk-slim

# Setează directorul de lucru
WORKDIR /app

# Copiază fișierele Maven/Gradle și codul
COPY . .

# Build (Maven example)
RUN ./mvnw clean package -DskipTests

# Expune portul
EXPOSE 8080

# Start serverul (ia fișierul .jar din target)
CMD ["java", "-jar", "target/myapp.jar"]
