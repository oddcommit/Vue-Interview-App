FROM ghcr.io/graalvm/graalvm-ce:ol8-java17-22.3.2
RUN mkdir /opt/app
COPY target/interview-example-project-{project_version}-RELEASE.jar /opt/app
WORKDIR /opt/app
EXPOSE 4667
CMD ["java", "-jar", "-Dspring.profiles.active=kubernetes", "interview-example-project-{project_version}-RELEASE.jar"]
