$PSDefaultParameterValues['*:Encoding'] = 'utf8'

Write-Output "Script to start docker and a postgres container for integration tests..."

if([string]::IsNullOrEmpty(($(docker stats --no-stream) 2>$null))) {
  Write-Output "Docker not started yet.. trying to start it.."
  Write-Output "Docker filepath: '$($Env:Programfiles)\Docker\Docker\Docker Desktop.exe'"
  Start-Process -WindowStyle hidden -FilePath "$($Env:Programfiles)\Docker\Docker\Docker Desktop.exe"
}

$currentRetries = 0
$maxRetries = 30


while ([string]::IsNullOrEmpty(($(docker stats --no-stream) 2>$null)) -and ($currentRetries -lt $maxRetries)) {
  $currentRetries++
  Write-Output "Waiting for docker desktop to get started. Max retries: $($maxRetries). Current retries: $($currentRetries)"
  Write-Output "sleep.."
  Start-Sleep -Seconds 2.0
}


if($currentRetries -eq $maxRetries) {
  exit 1
}

Write-Output "Docker successfully started"


Write-Output "Checking if postgres container is running..."
$is_container_running=$(docker ps | Select-String -Pattern "postgres" | Select-String -Pattern "0.0.0.0:5432->5432/tcp")

if([string]::IsNullOrEmpty($is_container_running)) {
  Write-Output "Container is not running.. trying starting it up"
  docker-compose -f "$($PSScriptRoot)\..\docker-compose.yml" up -d
  Write-Output "Container successfully started"
} else {
  Write-Output "Container is currently running no need to start it"
}

Write-Output "Script successfully ended"


