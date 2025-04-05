# Use a JDK base image that supports ARM architecture
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the application JAR file
COPY target/bankingtransactionAPI-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 9902

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
