set -e
mkdir -p temp
tar -xzf apache-maven-3.6.3-bin.tar.gz -C temp
export IMG_TAG=$1
if [ -z "$1" ]; then
  export IMG_TAG="$(date  +"%Y-%m-%d-%H-%M")"
fi

IMAGE="kgignatyev/db-manager:$IMG_TAG"

echo "building $IMAGE"

docker build -t $IMAGE .

echo "image has been built: $IMAGE"


docker build -t $IMAGE .
#docker push $IMAGE
