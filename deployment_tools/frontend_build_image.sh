#!/bin/bash
source ./helper_tools.sh

dockerHubAccount=$DOCKERHUB_USERNAME
scriptDirectory=$(get_relative_path_of_script_directory)
frontendTagName=interview-example-project-frontend
frontendReactFolder=$scriptDirectory/../frontend-react
packageJsonFile=$frontendReactFolder/package.json
projectFrontendVersion="$(cat "$packageJsonFile" | grep "\"version\"" | grep -Eo "[0-9]+.[0-9]+.[0-9]+")"

create_react_frontend_docker_file() {
    cd "$frontendReactFolder" || exit 1
    echo "Prepare frontend-react Dockerfile for production..."
    echo "Building docker image..."
    docker build -t $frontendTagName:"$projectFrontendVersion" -t $frontendTagName:latest .
    echo "Docker image build successful..."
    echo "Tagging frontend image..."
    docker tag $frontendTagName:"$projectFrontendVersion" $dockerHubAccount/$frontendTagName:"$projectFrontendVersion"
    docker tag $frontendTagName:"$projectFrontendVersion" $dockerHubAccount/$frontendTagName:latest
    echo "Tagging frontend successful"
    echo "Pushing image to docker hub..."
    docker push $dockerHubAccount/$frontendTagName:"$projectFrontendVersion"
    docker push $dockerHubAccount/$frontendTagName:latest
    echo "Push successful"
    cd "$scriptDirectory" || exit 1
}

create_react_frontend_docker_file

# End
echo "Build script end."
