FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/interview-example-project-$(projectVersion)-RELEASE.jar /opt/app
CMD ["java", "-jar", "interview-example-project-$(projectVersion)-RELEASE", "-Dspring.profiles.active=kubernetes"]
