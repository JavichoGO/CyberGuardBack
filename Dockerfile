FROM openjdk:17-jdk-bullseye

WORKDIR /app

#COPY ./target/questions-0.0.1-SNAPSHOT.jar /app
COPY ./target/questions-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "questions-0.0.1-SNAPSHOT.jar"]

#docker buildx build --platform linux/amd64,linux/arm64 -t your-image-name --push .

#docker manifest inspect openjdk:17-jdk-bullseye