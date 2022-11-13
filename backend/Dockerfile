FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/interview-example-project-{project_version}-RELEASE.jar /opt/app
WORKDIR /opt/app
CMD ["java", "-jar", "-Dspring.profiles.active=kubernetes", "interview-example-project-{project_version}-RELEASE.jar"]
