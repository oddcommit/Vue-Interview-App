FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/interview-example-project-{project_version}-RELEASE.jar /opt/app
WORKDIR /opt/app
CMD ["java", "-jar", "interview-example-project-{project_version}-RELEASE.jar", "-Dspring.profiles.active=kubernetes"]
