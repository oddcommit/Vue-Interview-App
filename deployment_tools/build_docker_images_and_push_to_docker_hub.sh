# Normally this script is running on an jenkins server or somewhere else
echo "Starting to build docker container releases and push them to docker hub..."

backendTagName=interview-example-project-backend
frontendTagName=interview-example-project-frontend

relDir="$(dirname -- "$0"; )";
cd "$relDir" || exit 1;
scriptDirectory="$( pwd; )";

pomFile=$scriptDirectory/../backend/pom.xml
backendDockerFile=$scriptDirectory/../backend/Dockerfile

projectVersion="$(cat "$pomFile" | grep "<project.version>" | grep -Eo "[0-9]+.[0-9]+.[0-9]+")"
echo "Backend project version is $projectVersion"

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
    echo "Trying to create a successfull maven build..."

    cd "$scriptDirectory"/../backend || exit 1
    ./mvnw clean install
    echo "Maven build successful!"
    cd "$scriptDirectory" || exit 1
}

create_docker_file() {
      cd "$scriptDirectory"/../backend || exit 1
      echo "Prepare Dockerfile for production..."
      replaceText "{project_version}" "$projectVersion" "$backendDockerFile"
      echo "Dockerfile preparation successful!"

      echo "Building docker image..."
      docker build --tag $backendTagName:"$projectVersion" --tag $backendTagName:latest .
      echo "Docker image build successful..."
      echo "Pushing image to docker hub..."
      docker push 21321321421441/$backendTagName:"$projectVersion"
      echo "Push successful"
      cd "$scriptDirectory" || exit 1
}

cleanup() {
    echo "cleanup started..."
    replaceText "<release.type>RELEASE" "<release.type>SNAPSHOT" "$pomFile"
    replaceText "spring.profiles.active=kubernetes" "spring.profiles.active=development" "$pomFile"
    replaceText "$projectVersion" "{project_version}" "$backendDockerFile"
    echo "cleanup ended..."
}

build_backend_maven_project
create_docker_file
cleanup


echo "Build script end."
