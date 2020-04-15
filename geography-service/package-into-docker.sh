set -e
set -x
export TAG=$1
if [ -z "$TAG" ]; then
  export TAG=`date -u +"%Y-%m-%d-%H-%M"`
fi

export IMAGE=kgignatyev/geograply-service:$TAG

# this is a shortcut
# normally we should package a given versioned artifact from repository
# so we can repackage with different base image


# todo: script to extract versions of interfaces implemented and required
# a simple groovy script

docker build -t $IMAGE \
 --build-arg IMPLEMENTS=geography-interface:tbd \
 --build-arg REQUIRES=- \
   .

docker push $IMAGE

echo $IMAGE
