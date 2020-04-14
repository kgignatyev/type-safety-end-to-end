#!/usr/bin/env bash

grpcwebproxy \
    --allow_all_origins \
    --server_http_debug_port=7000 \
    --backend_max_call_recv_msg_size=5242880 \
    --run_tls_server=false \
    --backend_addr=localhost:6565
