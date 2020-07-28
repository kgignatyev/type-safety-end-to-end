package com.kgi.geography_service.client

import io.grpc.Metadata

data class User( var id:String , var name:String, var jwt:String )



object CallContext {

    val user_id = Metadata.Key.of( "x-user-id", Metadata.ASCII_STRING_MARSHALLER )
    val user_name = Metadata.Key.of( "x-user-name", Metadata.ASCII_STRING_MARSHALLER )
    val user_authentication = Metadata.Key.of( "authorization", Metadata.ASCII_STRING_MARSHALLER )

    val anonymous = User("anonymous", "Anonymous User", "")

    val _currentUser = ThreadLocal.withInitial<User> { anonymous }
}
