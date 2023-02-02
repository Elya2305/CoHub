FROM gradle:jdk17 as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build

FROM eclipse-temurin:17.0.2_8-jdk-focal
COPY --from=gradleimage "/home/gradle/source/build/libs/int20h-1.0.0.jar" /app/
WORKDIR /app
ENTRYPOINT ["java", "-jar", "int20h-1.0.0.jar"]