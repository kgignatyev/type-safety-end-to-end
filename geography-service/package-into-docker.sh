set -e
set -x

export TAG=$1
if [ -z "$TAG" ]; then
  export TAG=`date -u +"%Y-%m-%d-%H-%M"`
fi

export IMAGE=kgignatyev/geography-service:$TAG

# this is a shortcut
# normally we should package a given versioned artifact from repository
# so we can repackage with different base image

mvn clean install -DskipTests
# a simple kotlin script to extract versions of interfaces implemented and required
mvn -B dependency:list > target/dependencies.txt
kotlinc -script find_implemented_interfaces.kts geography
source target/dependencies-info-export

docker build -t $IMAGE \
 --build-arg IMPLEMENTS=$IMPLEMENTS \
 --build-arg REQUIRES=$REQUIRES \
   .

docker push $IMAGE

echo $IMAGE
