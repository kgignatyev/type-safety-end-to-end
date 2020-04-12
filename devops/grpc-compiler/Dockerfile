FROM golang:1.13
ARG PROTOC_VERSION
ARG GRPCWEB_VERSION
ENV PROTOC_ZIP_URL=https://github.com/google/protobuf/releases/download/v$PROTOC_VERSION/protoc-$PROTOC_VERSION-linux-x86_64.zip
ENV PROTOC_ZIP=protoc-$PROTOC_VERSION-linux-x86_64.zip
ENV GRPC_WEB_URL=https://github.com/grpc/grpc-web/releases/download/$GRPCWEB_VERSION/protoc-gen-grpc-web-$GRPCWEB_VERSION-linux-x86_64
RUN apt update && apt-get install -y --no-install-recommends apt-utils
#docker layer
RUN apt install unzip  \
    && curl -OL  ${PROTOC_ZIP_URL} \
    && unzip -o ${PROTOC_ZIP} -d /usr/local bin/protoc \
    && chmod +x /usr/local/bin/protoc \
    && unzip -o ${PROTOC_ZIP} -d /usr/local include/* \
    && rm -f ${PROTOC_ZIP} \
    && apt -y install nodejs \
    && apt -y install npm \
    && npm install -g ts-protoc-gen \
    && curl -fsSL -o /usr/local/bin/protoc-gen-grpc-web ${GRPC_WEB_URL}  \
    && chmod +x /usr/local/bin/protoc-gen-grpc-web


