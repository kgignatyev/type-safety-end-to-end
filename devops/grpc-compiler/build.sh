docker build -t kgignatyev/protoc-grpcweb:v3 \
   --build-arg PROTOC_VERSION=3.11.4 \
   --build-arg GRPCWEB_VERSION=1.0.7 \
   .

echo "docker push kgignatyev/protoc-grpcweb:v3"
