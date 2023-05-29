Manual Steps for infrastructure creation (only executed once per cluster):


1. Install NGINX:
```
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
```
```
helm repo update
```
```
helm install ingress-nginx ingress-nginx/ingress-nginx --namespace ingress-nginx --create-namespace
```


2. Install Cert Manager:
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

----------------------------------------------

General Information:

Good tutorial for certificate creation:
https://cert-manager.io/docs/tutorials/acme/nginx-ingress/
