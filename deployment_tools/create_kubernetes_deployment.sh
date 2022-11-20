relDir="$(dirname -- "$0"; )";
cd "$relDir" || exit 1;
scriptDirectory="$( pwd; )";

kubernetesFolder=$scriptDirectory/../k8s

kubectl create -f $kubernetesFolder/01_posgres_secret.yaml
kubectl create -f $kubernetesFolder/02_posgres_volume_claim.yaml
kubectl create -f $kubernetesFolder/03_postgres_deployment.yaml
kubectl create -f $kubernetesFolder/04_postgres_cluster-ip-service.yaml
kubectl create -f $kubernetesFolder/05_backend_deployment.yaml
kubectl create -f $kubernetesFolder/06_backend_ip-service.yaml
kubectl create -f $kubernetesFolder/07_frontend_deployment.yaml
kubectl create -f $kubernetesFolder/08_frontend_ip-service.yaml
