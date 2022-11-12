echo "Script to start docker and a postgres container for integration tests..."

is_docker_running() {
  echo "Checking if docker is running..."
  docker info >/dev/null 2>&1
  if [[ $? -ne 0 ]]; then
    echo "Docker is not running, run it first and retry..."
    return 255
  else
    echo "Docker seems to be running no action to do..."
    return 0
  fi
}

is_container_running() {
  echo "Checking if container is running..."
  container_running=$(docker ps | grep postgres | grep "0.0.0.0:5432->5432/tcp")

  relDir="$(dirname -- "$0"; )";
  cd "$relDir" || exit 1;
  directory="$( pwd; )";

  echo "Directory is ${directory}";


  if [ -z "$container_running" ]; then
    echo "Container is not running.. trying starting it up"
    docker-compose -f "${directory}/../docker-compose.yml" up -d
  fi
  echo "Container is running successfully!"
}



start_docker() {
  if [ "$(uname)" == "Darwin" ]; then
    echo "Trying to start docker on mac os..."
    open -a Docker
  else
    echo "Trying to start docker on linux..."
    sudo systemctl start docker
  fi
}

is_docker_running

if [ $? -ne 0 ]; then
  echo "Docker seems not to be running..."
  start_docker
else
  echo "Docker is already running..."
fi


wait_docker_start_to_finish() {
  currentRetries=28
  maxRetries=30
  while [ -z "$(! docker stats --no-stream 2>/dev/null)" ] && [ $currentRetries -lt $maxRetries  ]; do
    ((currentRetries++))
    echo "Waiting for docker desktop to get started. Max retries: $maxRetries. Current retries: $currentRetries"
    echo "sleep..."
    sleep 2
  done

  if [ "$currentRetries" -eq $maxRetries ]; then
    echo "ERROR: Could not start docker. Exiting"
    exit 1
  fi
}

wait_docker_start_to_finish
echo "Docker successfully started"

is_container_running
