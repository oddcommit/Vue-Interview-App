kubectl create -f 01_posgres_secret.yaml
kubectl create -f 02_posgres_volume_claim.yaml
kubectl create -f 03_postgres_deployment.yaml
kubectl create -f 04_postgres_cluster-ip-service.yaml
kubectl create -f 05_backend_deployment.yaml
kubectl create -f 06_backend_ip-service.yaml
kubectl create -f 07_frontend_deployment.yaml
kubectl create -f 08_frontend_ip-service.yaml