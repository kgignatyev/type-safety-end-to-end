package com.kgi.geography_service.iam

import com.kgi.geography_service.iam.IAMConstants.cid
import io.grpc.*
import org.slf4j.MDC
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.util.logging.Logger


@Component
@Primary
open class AuthInterceptor : ServerInterceptor {

    @Autowired
    private lateinit var context: ApplicationContext

    override fun <ReqT, RespT> interceptCall(
            call: ServerCall<ReqT, RespT>,
            headers: Metadata,
            next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {

        val cid = extractCorrelationID(headers)
        MDC.put(IAMConstants.cid.name(), cid)

        val userId = extractUserIdentity(headers)
        return object : ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(
                next.startCall(call, headers)
        ) {
            private fun setupCallContext() {
                CallContext._correlationId.set(cid)
                CallContext._currentUserId.set(userId)
                MDC.put(IAMConstants.cid.name(), cid)
            }

            private fun cleanUpCallContext() {
                CallContext._correlationId.set("")
                CallContext._currentUserId.set("")
                MDC.put(IAMConstants.cid.name(), "")

            }

            override fun onMessage(message: ReqT) {
                setupCallContext()
                super.onMessage(message)
                cleanUpCallContext()
            }

            override fun onCancel() {
                setupCallContext()
                super.onCancel()
                cleanUpCallContext()
            }

            override fun onReady() {
                setupCallContext()
                super.onReady()
                cleanUpCallContext()
            }

            override fun onComplete() {
                setupCallContext()
                super.onComplete()
                cleanUpCallContext()
            }

            override fun onHalfClose() {
                setupCallContext()
                super.onHalfClose()
                cleanUpCallContext()
            }
        }
    }

    private fun extractUserIdentity(headers: Metadata): String {
        //just for demo purposes, we should not do it in real apps
        logger.info( headers.toString() )
        return "anonymous"
    }

    private fun extractCorrelationID(headers: Metadata): String {
        return headers[cid] ?: "none"
    }




    companion object {
        val logger = Logger.getLogger(AuthInterceptor::class.java.name)
    }
}
