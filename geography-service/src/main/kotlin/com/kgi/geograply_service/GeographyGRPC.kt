package com.kgi.geograply_service

import com.google.protobuf.Empty
import com.google.protobuf.StringValue
import com.kgi.geograply_service.services.GeographySvc
import io.grpc.stub.StreamObserver
import kgi.geography_api.GeographyGrpc
import kgi.geography_api.GeographyOuterClass
import net.devh.boot.grpc.server.service.GrpcService
import javax.annotation.Resource


@GrpcService()
class GeographyGRPC: GeographyGrpc.GeographyImplBase() {

    @Resource
    lateinit var geographySvc: GeographySvc

    fun <T> sendResponse(responseObserver: StreamObserver<T>?, value: T?) {
        responseObserver!!
        responseObserver.onNext(value)
        responseObserver.onCompleted()
    }

    override fun updateArea(request: GeographyOuterClass.Area, responseObserver: StreamObserver<GeographyOuterClass.Area>?) {
        sendResponse( responseObserver, geographySvc.update( request))
    }

    override fun createArea(request: GeographyOuterClass.Area, responseObserver: StreamObserver<GeographyOuterClass.Area>?) {
        sendResponse( responseObserver, geographySvc.create( request))
    }

    override fun getAreaByID(request: StringValue, responseObserver: StreamObserver<GeographyOuterClass.Area>?) {
        sendResponse( responseObserver, geographySvc.getAreaByID( request.value ))
    }

    override fun deleteAreaByID(request: StringValue, responseObserver: StreamObserver<Empty>?) {
        geographySvc.deleteAreaByID( request.value )
        sendResponse( responseObserver, Empty.getDefaultInstance() )
    }

    override fun findAreasContaining(request: GeographyOuterClass.LatLng, responseObserver: StreamObserver<GeographyOuterClass.AreasList>?) {
        sendResponse( responseObserver, geographySvc.findAreasContaining( request))
    }

    override fun findAreas(request: StringValue?, responseObserver: StreamObserver<GeographyOuterClass.AreasList>?) {
        sendResponse( responseObserver, geographySvc.findAreas( request.toString()))
    }
}
