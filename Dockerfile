# Step 1: Use a lightweight Java runtime as the base image
FROM eclipse-temurin:17-jre

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from your build directory to the container
# For Maven: target/*.jar | For Gradle: build/libs/*.jar
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Step 4: Expose the port your app runs on (default is 8080)
EXPOSE 8080

# Step 5: Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
