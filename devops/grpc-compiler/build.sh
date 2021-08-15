#!/usr/bin/env bash
set -x

export TAG=${1:-v4}
export IMAGE="kgignatyev/protoc-tools:${TAG}"

docker build -t ${IMAGE} \
      --build-arg PROTOC_VERSION=3.17.3  \
      --build-arg GRPCWEB_VERSION=1.2.1 \
      --build-arg GEDOC_VERSION=1.5.0 \
      --build-arg GRPCGATEWAY_VERSION=2.5.0 \
      --build-arg PROTOC_GO_VERSION=1.27.1 \
   .

echo "docker push ${IMAGE}"
