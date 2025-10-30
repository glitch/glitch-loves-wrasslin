FROM eclipse-temurin:25_36-jre-alpine-3.22

RUN apk update && apk upgrade

WORKDIR /app

COPY target/*springboot.jar /app/wrasslin-springboot.jar

# Entrypoint to run
ENTRYPOINT ["java", "-jar", "/app/wrasslin-springboot.jar"]