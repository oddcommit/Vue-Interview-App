TODO: Not implemented yet:

# build_docker_images_and_push_to_docker_hub.sh

Creates a docker image out of the frontend and backend.

1. In /backend/pom.xml change "<version>0.0.4-SNAPSHOT</version>" to "<version>0.0.4-RELEASE</version>"
2. In application.properties change "spring.profiles.active=development" to: "spring.profiles.active=kubernetes"
