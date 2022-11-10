echo "Script to start docker and a postgres container for integration tests..."

is_docker_running() {
  echo "Checking if docker is running..."
  docker_state=$(docker info >/dev/null 2>&1)
  if [[ $? -ne 0 ]]; then
    echo "Docker does not seem to be running, run it first and retry..."
    return 255
  else
    echo "Docker seems to be running no action to do..."
    return 0
  fi
}

is_container_running() {
  echo "Checking if container is running..."
  container_running=$(docker ps | grep postgres | grep "0.0.0.0:5432->5432/tcp")
  if [[ -z "$container_running" ]]; then
    echo "Container is not running.. trying starting it up"
    docker-compose -f ../docker-compose.yml up -d
  fi
  echo "Container is running successfully!"
}

wait_docker_start_to_finish() {
  while [[ -z "$(! docker stats --no-stream 2>/dev/null)" ]]; do
    printf "."
    sleep 1
  done
}

start_docker() {
  if [[ "$(uname)" == "Darwin" ]]; then
    echo "Trying to start docker on mac os..."
    open -a Docker
    wait_docker_start_to_finish
  else
    echo "Trying to start docker on linux..."
    sudo systemctl start docker
    wait_docker_start_to_finish
  fi
  echo -e "\nDocker successfully started"
}

is_docker_running

if [[ $? -ne 0 ]]; then
  echo "Docker seems not to be running..."
  start_docker
else
  echo "Docker is already running..."
fi

is_container_running
