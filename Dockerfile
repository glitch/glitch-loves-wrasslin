# FROM eclipse-temurin:25-jre-alpine-3.24
# RUN apk update && apk upgrade

# FROM bellsoft/hardened-liberica-runtime-container:jre-25-glibc as builder
FROM bellsoft/hardened-liberica-runtime-container:jre-25-nonroot-glibc

WORKDIR /app

COPY target/*springboot.jar /app/wrasslin-springboot.jar

# Entrypoint to run
ENTRYPOINT ["java", "-jar", "/app/wrasslin-springboot.jar"]
