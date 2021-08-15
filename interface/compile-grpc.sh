set -x
set -e

export PROTOS_SRC_DIR=src/main/proto
export TS_OUT=target/npm
export INTERFACE_VERSION=1.0.12
export INTERFACE_NAME=geography-interface
export PROTOS=geography.proto
export PWD=$(pwd)


export PROXY_SRC_OUT=target/proxy_src
export DOCS_OUT=target/docs
export SWAGGER_OUT_DIR=target/swagger

ensure_directories () {
  mkdir -p ${SWAGGER_OUT_DIR}
  mkdir -p ${TS_OUT}
  mkdir -p ${PROXY_SRC_OUT}
  mkdir -p ${DOCS_OUT}
}

collect_proto_includes(){
  PROTO_INCLUDE="-I /usr/local/include/protos "
for dir in target/protoc-dependencies/*; do
    PROTO_INCLUDE="-I $dir "${PROTO_INCLUDE}
done
export PROTO_INCLUDE
echo $PROTO_INCLUDE
}




gen_docs () {
  protoc  $PROTO_INCLUDE -I $PROTOS_SRC_DIR "$@"
}

gen_proto_docs () {
  mkdir -p target/docs
  gen_docs --doc_out=target/docs --doc_opt=html,index.html $PROTOS
  cp  target/docs/index.html target/docs/index.original.html
  cat target/docs/index.original.html | \
    sed  -e "s/>Table of Contents</> Version: ${INTERFACE_VERSION} generated: $(date) </g" | \
    sed  -e "s/>Protocol Documentation</> API ${INTERFACE_NAME}  </g" > target/docs/index.html
  cp target/docs/index.html target/docs/index-${INTERFACE_VERSION}.html
  gen_docs --doc_out=target/docs --doc_opt=markdown,interface.md \
  $PROTOS
}




gen_rest_proxy () {
   if test -f "src/main/go"; then
     cp -R src/main/go/* $PROXY_SRC_OUT
   fi
   protoc $PROTO_INCLUDE -I $PROTOS_SRC_DIR \
     --grpc-gateway_out $PROXY_SRC_OUT/ \
     --grpc-gateway_opt logtostderr=true \
     --grpc-gateway_opt paths=source_relative \
     --grpc-gateway_opt generate_unbound_methods=true \
     --go_out $PROXY_SRC_OUT/ --go_opt paths=source_relative \
     --go-grpc_out $PROXY_SRC_OUT/ --go-grpc_opt paths=source_relative \
   $PROTOS
}

gen_openapi_docs () {
     protoc  $PROTO_INCLUDE -I $PROTOS_SRC_DIR\
            --openapiv2_out ${SWAGGER_OUT_DIR} \
            --openapiv2_opt logtostderr=true  \
            --openapiv2_opt generate_unbound_methods=true  \
            --openapiv2_opt fqn_for_openapi_name=true  \
      $PROTOS
}

gen_ts_stubs () {

    protoc \
    --js_out=import_style=commonjs,binary:$TS_OUT \
    --grpc-web_out=import_style=commonjs+dts,mode=grpcwebtext:$TS_OUT \
    $PROTO_INCLUDE \
    -I=$PROTOS_SRC_DIR \
    $PROTOS
    cp target/docs/index.html  $TS_OUT/interface.html
    cp target/docs/interface.md $TS_OUT/


}



deploy_to_repo () {
   mvn deploy
   cd  $TS_OUT
   npm publish
   cd -
}

ensure_directories

collect_proto_includes

gen_proto_docs

gen_ts_stubs

gen_openapi_docs

gen_rest_proxy
