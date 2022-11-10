echo "Script to start docker and a postgres container for integration tests..."

if(([string]::IsNullOrEmpty($(docker stats --no-stream 2>$null)))) {
  echo "Docker not started yet.. trying to start it.."
  Start-Process -FilePath "c:\PROGRA~1\Docker\Docker\Docker Desktop.exe" -NoNewWindow
}

$maxRetries = 30
$currentRetries = 0


while (([string]::IsNullOrEmpty($(docker stats --no-stream 2>$null))) -or $currentRetries -eq $maxRetries) {
  $currentRetries++
  echo "Waiting for docker desktop to get started. Max retries: $($maxRetries). Current retries: $($currentRetries)"
  echo "sleep.."
  Start-Sleep -Seconds 2.0
}


if($currentRetries -eq $maxRetries) {
  [Environment]::Exit(1)
}

echo "Docker successfully started"


echo "Checking if postgres container is running..."
$is_container_running=$(docker ps | Select-String -Pattern "postgres" | Select-String -Pattern "0.0.0.0:5432->5432/tcp")

if([string]::IsNullOrEmpty($is_container_running)) {
  echo "Container is not running.. trying starting it up"
  docker-compose -f ../docker-compose.yml up -d
  echo "Container successfully started"
} else {
  echo "Container is currently running no need to start it"
}

echo "Script successfully ended"


