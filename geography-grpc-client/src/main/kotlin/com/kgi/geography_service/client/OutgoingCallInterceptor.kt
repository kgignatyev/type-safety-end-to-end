package com.kgi.geography_service.client


import io.grpc.*
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor
import java.util.logging.Logger

@GrpcGlobalClientInterceptor
class OutgoingCallInterceptor : ClientInterceptor {

    override fun <ReqT, RespT> interceptCall(
            method: MethodDescriptor<ReqT, RespT>,
            callOptions: CallOptions,
            next: Channel
    ): ClientCall<ReqT, RespT> {
        return object : ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {

            override fun start(responseListener: ClientCall.Listener<RespT>, headers: Metadata) {

                val currentUser = CallContext._currentUser.get()
                headers.put( CallContext.user_id, currentUser.id)
                headers.put( CallContext.user_name, currentUser.name)
                headers.put( CallContext.user_authentication, "Bearer ${currentUser.jwt}")


                super.start(object : ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    override fun onHeaders(headers: Metadata?) {
                        /**
                         * if you don't need receive header from server,
                         * you can use [io.grpc.stub.MetadataUtils.attachHeaders]
                         * directly to send header
                         */
                        logger.finest("headers received from server:$headers")
                        super.onHeaders(headers)
                    }
                }, headers)
            }
        }
    }



    companion object {
        val logger = Logger.getLogger(OutgoingCallInterceptor::class.java.name)
    }


}
