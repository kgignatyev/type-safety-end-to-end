FROM golang:1.16.6
ARG PROTOC_VERSION
ARG GRPCWEB_VERSION
ARG GRPCGATEWAY_VERSION
ARG GEDOC_VERSION
ARG PROTOC_GO_VERSION
ENV NODE_OPTIONS="--max_old_space_size=2048"

ENV PROTOC_ZIP_URL=https://github.com/google/protobuf/releases/download/v$PROTOC_VERSION/protoc-$PROTOC_VERSION-linux-x86_64.zip
ENV PROTOC_ZIP=protoc-$PROTOC_VERSION-linux-x86_64.zip
ENV GRPC_WEB_URL=https://github.com/grpc/grpc-web/releases/download/$GRPCWEB_VERSION/protoc-gen-grpc-web-$GRPCWEB_VERSION-linux-x86_64
ENV GRPC_GATEWAY_URL=https://github.com/grpc-ecosystem/grpc-gateway/releases/download/v${GRPCGATEWAY_VERSION}/protoc-gen-grpc-gateway-v${GRPCGATEWAY_VERSION}-linux-x86_64
ENV GRPC_OPENAPI_URL=https://github.com/grpc-ecosystem/grpc-gateway/releases/download/v${GRPCGATEWAY_VERSION}/protoc-gen-openapiv2-v${GRPCGATEWAY_VERSION}-linux-x86_64
ENV GENDOC_TGZ_URL=https://github.com/pseudomuto/protoc-gen-doc/releases/download/v${GEDOC_VERSION}/protoc-gen-doc-${GEDOC_VERSION}.linux-amd64.go1.16.6.tar.gz
ENV PROTOC_GO_TGZ_URL=https://github.com/protocolbuffers/protobuf-go/releases/download/v${PROTOC_GO_VERSION}/protoc-gen-go.v${PROTOC_GO_VERSION}.linux.amd64.tar.gz

COPY protos/googleapis-master/google /usr/local/include/protos/google/
COPY protos/grpc-gateway/protoc-gen-openapiv2 /usr/local/include/protos/protoc-gen-openapiv2/

RUN curl -sL https://deb.nodesource.com/setup_16.x | bash -;
RUN apt update && apt-get install -y --no-install-recommends apt-utils jq moreutils
#docker layer
RUN apt -y install unzip zip jq  \
    && curl -OL  ${PROTOC_ZIP_URL} \
    && unzip -o ${PROTOC_ZIP} -d /usr/local bin/protoc \
    && unzip -o ${PROTOC_ZIP} -d /usr/local include/* \
    && rm -f ${PROTOC_ZIP} \
    && curl -fsSL   -o /gendoc.tgz  ${GENDOC_TGZ_URL} \
    && tar -xzf /gendoc.tgz --strip-components=1 -C /usr/local/bin/ \
    && curl -fsSL   -o /protoc_go.tgz  ${PROTOC_GO_TGZ_URL} \
    && tar -xzf /protoc_go.tgz -C /usr/local/bin/ \
    &&  go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest \
    && curl -fsSL -o /usr/local/bin/protoc-gen-grpc-web ${GRPC_WEB_URL}  \
    && curl -fsSL -o /usr/local/bin/protoc-gen-grpc-gateway ${GRPC_GATEWAY_URL}  \
    && curl -fsSL -o /usr/local/bin/protoc-gen-openapiv2 ${GRPC_OPENAPI_URL}  \
    && curl -fsSL -o /usr/local/bin/yq https://github.com/mikefarah/yq/releases/download/v4.11.2/yq_linux_amd64  \
    && apt -y install nodejs \
    && npm install -g npm-cli-adduser \
    && npm install -g ts-protoc-gen \
    && chmod +x /usr/local/bin/*

WORKDIR /root

