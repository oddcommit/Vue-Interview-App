1. Manual Steps for infrastructure creation (only executed once per cluster)

1.1 Install NGINX:
```
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
```
```
helm repo update
```
```
helm install ingress-nginx ingress-nginx/ingress-nginx --namespace ingress-nginx --create-namespace
```


1.2 Install Cert Manager:
```
helm repo add jetstack https://charts.jetstack.io
```
```
helm repo update
```
```
helm install \
cert-manager jetstack/cert-manager \
--namespace cert-manager \
--create-namespace \
--version v1.12.0 \
--set installCRDs=true
```

2. Application installation:

2.1 Navigate into the "k8s" folder then run:

```
helm upgrade --dry-run --install --create-namespace --namespace interview-example-project --values interview-example-helm-chart/values-PRODUCTION.yaml interview-example-helm-chart ./interview-example-helm-chart
```

2.2 After successful test run without dry-run
```
helm upgrade --install --create-namespace --namespace interview-example-project --values interview-example-helm-chart/values-PRODUCTION.yaml interview-example-helm-chart ./interview-example-helm-chart
```


----------------------------------------------

General Information:

Good tutorial for certificate creation:
https://cert-manager.io/docs/tutorials/acme/nginx-ingress/
