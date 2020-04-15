set -e
set -x
export TAG=$1
if [ -z "$TAG" ]; then
  export TAG=`date -u +"%Y-%m-%d-%H-%M"`
fi

export IMAGE=kgignatyev/istio-grpc-web-proxy:$TAG

docker build -t $IMAGE .

docker push $IMAGE

echo $IMAGE
