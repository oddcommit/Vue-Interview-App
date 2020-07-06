# Normally this script is running on an jenkins server or somewhere else
set -e
trap "cleanup" ERR

pomFile=pom.xml
backendDockerFile=../backend/Dockerfile

replaceText() {
    # Mac got a slightly different SED (FreeBSD sed)
    if [[ "$(uname)" == "Darwin" ]]; then
        sed -i '' "s/$1/$2/g" $3
    else
        sed -i "s/$1/$2/g" $3
    fi
}

cleanup() {
    echo "cleanup started..."
    currentDirectory="$(pwd)"

    if [[ $string == *"backend"* ]]; then
        cd ../deployment_tools
    fi

    replaceText "<release.type>RELEASE" "<release.type>SNAPSHOT" ../backend/$pomFile
    replaceText "spring.profiles.active=kubernetes" "spring.profiles.active=development" ../backend/$pomFile
    replaceText "$projectVersion" '$(projectVersion)' ../backend/$backendDockerFile
    echo "cleanup ended..."
}

projectVersion="$(cat ../backend/$pomFile | grep '<project.version>' | grep -Eo '[0-9]+.[0-9]+.[0-9]+')"

backendTagName=21321321421441/interview-example-project-backend
frontendTagName=21321321421441/interview-example-project-frontend

echo "Starting to build docker container releases and push them to docker hub"
docker info &>/dev/null

build_backend_docker_container() {
    echo "Prepare 'pom.xml' file for production..."
    replaceText "<release.type>SNAPSHOT" "<release.type>RELEASE" $pomFile
    replaceText "spring.profiles.active=development" "spring.profiles.active=kubernetes" $pomFile
    echo "Trying to create a successfull maven build..."
    mvn clean install
    echo "Maven build successful!"
    echo "Prepare Dockerfile for production..."
    projectVersion="$(cat $pomFile | grep '<project.version>' | grep -Eo '[0-9]+.[0-9]+.[0-9]+')"
    replaceText '$(projectVersion)' "$projectVersion" $backendDockerFile
    echo "Dockerfile preparation successful!"
    echo "Building docker image..."
    docker build --tag $backendTagName:$projectVersion --tag $backendTagName:latest .
    echo "Docker image build successful..."
    echo "Pushing image to docker hub..."
    docker push 21321321421441/$backendTagName:$projectVersion
    echo "Push successful"

    # echo "Prepareing kubernetes backend yaml file..."
    # TODO: This has to be implemented
}

cd ../backend
build_backend_docker_container

cleanup

echo "End building container."
