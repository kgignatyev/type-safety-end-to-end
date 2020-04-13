package com.kgi.geograply_service.iam

import java.util.function.Supplier


object CallContext {
    val _currentUserId: ThreadLocal<String> = ThreadLocal.withInitial(Supplier { "anonymous" })
    val _correlationId: ThreadLocal<String> = ThreadLocal.withInitial { "none" }

    fun currentUserId(): String {
        return _currentUserId.get()
    }

    fun correlationId(): String {
        return _correlationId.get()
    }


}
