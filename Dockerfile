FROM openjdk:17-slim
COPY "./target/crud-0.0.1-SNAPSHOT.jar" "app.jar"
COPY "./clasificador/haarcascade_frontalface_default.xml" "/clasificador/"
EXPOSE 4000:4000
ENTRYPOINT ["java","-jar","app.jar"]