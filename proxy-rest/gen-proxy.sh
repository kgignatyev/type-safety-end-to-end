
mkdir -p gen/swagger
mkdir -p gen/go

protoc -I ./proto_deps/ -I ../interface/src/main/proto/ \
   --grpc-gateway_out=logtostderr=true,paths=source_relative:./gen/go/ \
   --go_out=plugins=grpc,paths=source_relative:./gen/go/ \
   --swagger_out=logtostderr=true:./gen/swagger \
  geography.proto


go build -i -o $HOME/go/bin/kgi_geography_rest_proxy
