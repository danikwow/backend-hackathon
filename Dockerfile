WORKDIR hackathon-backend

cope . /hackathon-backend

FROM adoptopenjdk:17-jdk-hotspot

ENV PORT=3030

RUN gradle clean build

CMD ["java", "-jar", "HackathonBackendApplication.jar"]
