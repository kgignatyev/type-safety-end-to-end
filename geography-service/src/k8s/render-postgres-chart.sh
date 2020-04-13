mkdir  -p target

helm repo add bitnami https://charts.bitnami.com/bitnami
helm fetch --untar --untardir charts 'bitnami/postgresql'
helm template --name-template dev  --values pg-values.yaml  charts/postgresql > target/pg-k8s-descriptor.yaml

echo "chart rendered to target/pg-k8s-descriptor.yaml"


