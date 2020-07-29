package com.kgi.geography_service.client

import com.google.protobuf.StringValue
import org.junit.Assert.assertEquals
import kgi.geography_api.GeographyGrpc
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.File
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [GeographyServiceGrpcClient::class])
class AuthenticatedCallTest {

    @Resource
    lateinit var connectors: GrpcServicesConnector


    var user: User = CallContext.anonymous



    @Before
    fun setCallContext() {
        val jwt = File("config/jwt.txt").readText().replace("[\n\r]".toRegex(),"")
        user = User("test", "test", jwt)
        CallContext._currentUser.set(user)
    }

    @Test
    fun testListAreas() {
        testWithStub( connectors.geographyServiceStub )

        testWithStub( connectors.geographyAuthServiceStub )

    }


    @Test
    fun testListAreasDoesNotWorkWithoutAuthentication() {

       val exception = assertThrows<Throwable>{
           CallContext._currentUser.get().jwt = ""
           testWithStub(connectors.geographyAuthServiceStub)
       }
       println( exception)

    }

    fun testWithStub( stub: GeographyGrpc.GeographyBlockingStub){
        val foundAreas = stub.findAreas(StringValue.of(""))
        foundAreas.itemsList.forEachIndexed { index, area ->
            println("found area ${index}:  ${area}")
        }
    }
}
