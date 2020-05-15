package com.kgi.geography_service.services

import com.kgi.geography_service.dao.XY
import com.kgi.geography_service.dao.model.AreaRecord
import com.kgi.geography_service.impl.GeographySvcImpl
import kgi.geography_api.GeographyOuterClass
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service
import javax.annotation.Resource


@Service
class GeographySvc {

    @Resource
    lateinit var conversionService: ConversionService

    @Resource
    lateinit var geographySvcImpl: GeographySvcImpl

    fun findAreas(text: String): GeographyOuterClass.AreasList {
       val areasGrpc = geographySvcImpl.findAreas( text ).map { conversionService.convert(it, GeographyOuterClass.Area::class.java) }
       return GeographyOuterClass.AreasList.newBuilder().addAllItems( areasGrpc ).build()

    }

    fun findAreasContaining(point: GeographyOuterClass.LatLng): GeographyOuterClass.AreasList {
        val areasGrpc = geographySvcImpl.findAreasContaining( XY( point.lng, point.lat) ).map { conversionService.convert(it, GeographyOuterClass.Area::class.java) }
        return GeographyOuterClass.AreasList.newBuilder().addAllItems( areasGrpc ).build()

    }

    fun update(request: GeographyOuterClass.Area): GeographyOuterClass.Area {
        val a = conversionService.convert(  request, AreaRecord::class.java)
        geographySvcImpl.update( a!! )
        return request
    }

    fun create(request: GeographyOuterClass.Area): GeographyOuterClass.Area {
        val a = conversionService.convert(  request, AreaRecord::class.java)!!
        val res = geographySvcImpl.create( a )
        return conversionService.convert( res, GeographyOuterClass.Area::class.java)!!
    }

    fun getAreaByID(id: String): GeographyOuterClass.Area {
        val res = geographySvcImpl.getAreaByID( id )
        return conversionService.convert( res, GeographyOuterClass.Area::class.java)!!
    }

    fun deleteAreaByID(id: String) {
        geographySvcImpl.deleteAreaByID( id )
    }

}
