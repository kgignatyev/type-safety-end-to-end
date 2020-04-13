set -x
export SRC_DIR=src/main/proto
export TS_OUT_DIR=target/ts_grpcweb

export PWD=$(pwd)


gen_java_stubs () {
#mkdir -p ${JAVA_DST_DIR}
#protoc -I=$SRC_DIR --java_out=$JAVA_DST_DIR $SRC_DIR/geography.proto
mvn clean install
}

export_version_info(){
  mvn  -q -N org.codehaus.mojo:exec-maven-plugin:1.6.0:exec -Dexec.executable='echo'    \
        -Dexec.args='export INTERFACE_VERSION=${project.version}; export INTERFACE_NAME=${project.artifactId}' > target/version_info
}

gen_ts_stubs () {

  mkdir -p  $PWD/${TS_OUT_DIR}
  docker run --rm  -v "$PWD":/usr/src/nodeapp -w /usr/src/nodeapp \
    kgignatyev/protoc-grpcweb:v3 \
    protoc --plugin=protoc-gen-ts=/usr/local/bin/protoc-gen-ts \
    --js_out=import_style=commonjs,binary:$TS_OUT_DIR \
    --ts_out=service=grpc-web:$TS_OUT_DIR \
    -I=${SRC_DIR} $SRC_DIR/geography.proto

#    set names
  cat package.json| sed -e "s/INTERFACE_VERSION/${INTERFACE_VERSION}/g"  | \
     sed -e "s/INTERFACE_NAME/${INTERFACE_NAME}/g" > $TS_OUT_DIR/package.json
}


deploy_to_repo () {
#  mvn deploy
   cd  $TS_OUT_DIR
   npm publish
   cd -
}

gen_java_stubs
export_version_info

source target/version_info

gen_ts_stubs

deploy_to_repo
