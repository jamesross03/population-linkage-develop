# Use Maven to run
FROM maven:3.9.9-eclipse-temurin-21-jammy

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml, source code and entrypoint
COPY pom.xml .
COPY src ./src
COPY docker/entrypoint.sh .

# Install dependencies
RUN mvn clean install

RUN chmod +x ./entrypoint.sh ./src/main/scripts/**/*.sh

ENTRYPOINT ["./entrypoint.sh"]