FROM adoptopenjdk/openjdk11:latest
COPY target/*.jar app.jar
ENV JAVA_OPTS=""
EXPOSE 9000
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]