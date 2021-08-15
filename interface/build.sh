#!/usr/bin/env bash
set -e
set -x

mvn clean package grpc-compiler-script:generate-build-script
docker run \
  -v $(pwd):/root \
  -v $HOME/.m2:/root/.m2 \
  -v $HOME/.npmrc:/root/.npmrc \
  --rm kgignatyev/protoc-tools:v4 bash -c ./compile-grpc.sh
mvn deploy
cd target/npm
npm publish
