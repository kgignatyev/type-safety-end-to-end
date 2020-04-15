set -x

helm dependencies update service-charts/geography
helm template --name-template r1 \
    -f geography-values.yaml \
    service-charts/geography > target/geography-descriptors.yaml
