kubectl create -f posgres_secret.yml
kubectl create -f posgres_volume_claim.yaml 
kubectl create -f postgres_deployment.yaml
kubectl create -f postgres_cluster-ip-service.yaml
