package com.kgi.geograply_service.iam

import io.grpc.Metadata


object IAMConstants {
    val user_id = Metadata.Key.of( "x-user-id", Metadata.ASCII_STRING_MARSHALLER )
    val user_sub = Metadata.Key.of( "x-sub", Metadata.ASCII_STRING_MARSHALLER )
    val cid: Metadata.Key<String> = Metadata.Key.of( "cid", Metadata.ASCII_STRING_MARSHALLER )
}
