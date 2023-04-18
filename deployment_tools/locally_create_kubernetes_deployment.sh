source ./helper_tools.sh
scriptDirectory=$(get_relative_path_of_script_directory)

kubernetesFolder=$scriptDirectory/../k8s

kubectl create -f $kubernetesFolder/01_posgres_secret.yaml
kubectl create -f $kubernetesFolder/02_postgres_stateful_set.yaml

while [[ $(kubectl get statefulset postgres -o 'jsonpath={.status.readyReplicas}') != $(kubectl get statefulset postgres -o 'jsonpath={.spec.replicas}') ]]; do
  echo "Waiting for 'postgres'-StatefulSet to complete..."
  sleep 5
done

kubectl create -f $kubernetesFolder/03_postgres_cluster-ip-service.yaml
kubectl create -f $kubernetesFolder/04_backend_deployment.yaml
kubectl create -f $kubernetesFolder/05_backend_ip-service.yaml
kubectl create -f $kubernetesFolder/06_frontend_deployment.yaml
kubectl create -f $kubernetesFolder/07_frontend_ip-service.yaml
kubectl create -f $kubernetesFolder/08_ingress-service.yaml
