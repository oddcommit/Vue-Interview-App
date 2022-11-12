Write-Output "Shutting down previously opened containers..."

$adminerContainerName=$(docker ps -aqf "name=backend-admin")

if([string]::IsNullOrEmpty($adminerContainerName)) {
  Write-Output "Can not shut down adminer correctly because it does not exist."
  exit 1
}

$backendContainerName=$(docker ps -aqf "name=backend-db-1")

if([string]::IsNullOrEmpty($backendContainerName)) {
  Write-Output "Can not shut down postgres correctly because it does not exist."
  exit 1
}


Write-Output "shutting down adminer with container id: $($adminerContainerName)"
docker stop $adminerContainerName
Write-Output "shutting down adminer with container id: $($backendContainerName)"
docker stop $backendContainerName
Write-Output "Script successfully ended and integration test containers shut down correctly."



