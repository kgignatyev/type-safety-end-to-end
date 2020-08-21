
mkdir -p gen/swagger
mkdir -p gen/go

protoc -I ./proto_deps/ -I ../interface/src/main/proto/ \
   --grpc-gateway_out=logtostderr=true,paths=source_relative:./kgi_geography_api/ \
   --go_out=plugins=grpc,paths=source_relative:./kgi_geography_api/ \
   --swagger_out=logtostderr=true:./gen/swagger \
  geography.proto

export BIN_TARGET=$HOME/go/bin/kgi_geography_rest_proxy

go build -i -o $BIN_TARGET

echo "rest proxy built at $BIN_TARGET"
