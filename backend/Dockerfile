FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/$(replace-this-with-build-jar) /opt/app
CMD ["java", "-jar", "/opt/app/interview-example-project-0.0.1-SNAPSHOT.jar"]
