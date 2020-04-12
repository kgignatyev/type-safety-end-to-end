set -x
export SRC_DIR=protos
export JAVA_DST_DIR=target/java_out
export PWD=$(pwd)

mkdir -p ${JAVA_DST_DIR}
protoc -I=$SRC_DIR --java_out=$JAVA_DST_DIR $SRC_DIR/geography.proto


mvn -f proto-geography-compiler.xml install


gen_ts_stubs () {
  export TS_OUT_DIR=target/ts_grpcweb
  mkdir -p  $PWD/${TS_OUT_DIR}
  docker run --rm  -v "$PWD":/usr/src/nodeapp -w /usr/src/nodeapp \
    kgignatyev/protoc-grpcweb:v3 \
    protoc --plugin=protoc-gen-ts=/usr/local/bin/protoc-gen-ts \
    --js_out=import_style=commonjs,binary:$TS_OUT_DIR \
    --ts_out=service=grpc-web:$TS_OUT_DIR \
    -I=${SRC_DIR} $SRC_DIR/geography.proto
  cp $SRC_DIR/package.json $TS_OUT_DIR/package.json
}

gen_ts_stubs
