kubectl create -f posgres_secret.yaml
kubectl create -f posgres_volume_claim.yaml 
kubectl create -f postgres_deployment.yaml
kubectl create -f postgres_cluster-ip-service.yaml
kubectl create -f backend_deployment.yaml
kubectl create -f backend_ip-service.yaml
kubectl create -f frontend_deployment.yaml
kubectl create -f frontend_ip-service.yaml