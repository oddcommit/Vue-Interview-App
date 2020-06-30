FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY target/$(replace-this-with-build-jar) /opt/app
CMD ["java", "-jar", "$(replace-this-with-build-jar)", "-Dspring.profiles.active=kubernetes"]
