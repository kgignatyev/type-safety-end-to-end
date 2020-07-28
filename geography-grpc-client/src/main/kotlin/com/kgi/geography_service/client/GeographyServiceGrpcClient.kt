package com.kgi.geography_service.client

import kgi.geography_api.GeographyGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service

@Service
class GrpcServicesConnector{

    @GrpcClient(value="geography-grpc-server",
            interceptors =  [OutgoingCallInterceptor::class])
    lateinit var geographyServiceStub: GeographyGrpc.GeographyBlockingStub


    @GrpcClient(value="geography-grpc-server-auth",
            interceptors =  [OutgoingCallInterceptor::class])
    lateinit var geographyAuthServiceStub: GeographyGrpc.GeographyBlockingStub

}

@SpringBootApplication
@Configuration
@EnableAsync(proxyTargetClass = true)
class GeographyServiceGrpcClient{
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(GeographyServiceGrpcClient::class.java, *args)
        }
    }
}
