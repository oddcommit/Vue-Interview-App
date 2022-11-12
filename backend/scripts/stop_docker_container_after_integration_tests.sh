echo "Shutting down previously opened containers..."

adminerContainerName=$(docker ps -aqf "name=backend-admin")

if [ -z "$adminerContainerName" ]; then
  echo "Can not shut down adminer correctly because it does not exist."
  exit 1
fi

backendContainerName=$(docker ps -aqf "name=backend-db-1")

if [ -z "$backendContainerName" ]; then
  echo "Can not shut down postgres correctly because it does not exist."
  exit 1
fi

echo "shutting down adminer with container id: $adminerContainerName"
docker stop "$adminerContainerName"
echo "shutting down adminer with container id: $backendContainerName"
docker stop "$backendContainerName"
echo "Script successfully ended and integration test containers shut down correctly."

