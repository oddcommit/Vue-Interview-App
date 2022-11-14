# Normally this script is running on an jenkins server or somewhere else
echo "Starting to build docker container releases and push them to docker hub..."

dockerHubAccount=21321321421441
relDir="$(dirname -- "$0"; )";
cd "$relDir" || exit 1;
scriptDirectory="$( pwd; )";

# Build backend
backendTagName=interview-example-project-backend
backendFolder=$scriptDirectory/../backend
pomFile=$backendFolder/pom.xml
backendDockerFile=$backendFolder/Dockerfile

projectBackendVersion="$(cat "$pomFile" | grep "<project.version>" | grep -Eo "[0-9]+.[0-9]+.[0-9]+")"
echo "Backend project version is $projectBackendVersion"

replaceText() {
    # Mac got a slightly different SED (FreeBSD sed)
    if [[ "$(uname)" == "Darwin" ]]; then
      sed -i '' "s/$1/$2/g" "$3"
    else
      sed -i "s/$1/$2/g" "$3"
    fi
}

build_backend_maven_project() {
    echo "Prepare 'pom.xml' file for production..."
    replaceText "<release.type>SNAPSHOT" "<release.type>RELEASE" "$pomFile"
    replaceText "spring.profiles.active=development" "spring.profiles.active=kubernetes" "$pomFile"
    echo "Trying to create a successful maven build..."

    cd "$scriptDirectory"/../backend || exit 1
    ./mvnw clean install
    echo "Maven build successful!"
    cd "$scriptDirectory" || exit 1
}

create_backend_docker_file() {
    cd "$scriptDirectory"/../backend || exit 1
    echo "Prepare backend Dockerfile for production..."
    replaceText "{project_version}" "$projectBackendVersion" "$backendDockerFile"
    echo "Dockerfile preparation successful!"

    echo "Building docker image..."
    docker build -t $backendTagName:"$projectBackendVersion" -t $backendTagName:latest .
    echo "Docker image build successful..."
    echo "Tagging backend image..."
    docker tag $backendTagName:"$projectBackendVersion" $dockerHubAccount/$backendTagName:"$projectBackendVersion"
    docker tag $backendTagName:"$projectBackendVersion" $dockerHubAccount/$backendTagName:latest
    echo "Tagging successful"
    echo "Pushing backend image to docker hub..."
    docker push $dockerHubAccount/$backendTagName:"$projectBackendVersion"
    docker push $dockerHubAccount/$backendTagName:latest
    echo "Push successful"
    cd "$scriptDirectory" || exit 1
}

cleanup_backend() {
    echo "cleanup started..."
    replaceText "<release.type>RELEASE" "<release.type>SNAPSHOT" "$pomFile"
    replaceText "spring.profiles.active=kubernetes" "spring.profiles.active=development" "$pomFile"
    replaceText "$projectBackendVersion" "{project_version}" "$backendDockerFile"
    echo "cleanup ended..."
}

build_backend_maven_project
create_backend_docker_file
cleanup_backend

# Build react-frontend
frontendTagName=interview-example-project-frontend
frontendReactFolder=$scriptDirectory/../frontend-react
packageJsonFile=$frontendReactFolder/package.json
projectFrontendVersion="$(cat "$packageJsonFile" | grep "\"version\"" | grep -Eo "[0-9]+.[0-9]+.[0-9]+")"

build_frontend_react_project() {
    echo "Prepare 'package.json' file for production..."
    echo "Trying to create a successful frontend-react build..."
    cd "$frontendReactFolder" || exit 1
    echo "Run: npm install"
    npm install
    echo "Run: npm build"
    npm run build
    echo "Frontend build successful!"
    cd "$scriptDirectory" || exit 1
}

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

build_frontend_react_project
create_react_frontend_docker_file

# End
echo "Build script end."
