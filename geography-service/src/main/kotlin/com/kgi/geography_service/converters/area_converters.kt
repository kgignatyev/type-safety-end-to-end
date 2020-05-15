package com.kgi.geography_service.converters

import com.kgi.geography_service.dao.Polygon
import com.kgi.geography_service.dao.XY
import com.kgi.geography_service.dao.model.AreaRecord
import kgi.geography_api.GeographyOuterClass

import org.springframework.core.convert.converter.Converter


class DAOToArea  : Converter<AreaRecord, GeographyOuterClass.Area> {
    override fun convert(s: AreaRecord): GeographyOuterClass.Area {
        val r = GeographyOuterClass.Area.newBuilder()
        r.id = s.id
        r.name = s.name
        r.polygon = toPolyGrpc( s.polygon )
        r.areaType = s.areaType
        return r.build()
    }

}

class AreaToDAO :Converter< GeographyOuterClass.Area, AreaRecord > {

    override fun convert(s: GeographyOuterClass.Area): AreaRecord {
        return AreaRecord( s.id,  s.name, toPoly( s.polygon), areaType =  s.areaType )
    }

}

fun toPolyGrpc(polygon: Polygon?): GeographyOuterClass.Polygon {
    return if(polygon != null  ) {
        val vertices = polygon.vertices.map { p ->
            GeographyOuterClass.LatLng.newBuilder().setLat( p.y ).setLng(p.x).build() }
        GeographyOuterClass.Polygon.newBuilder().addAllVertices( vertices).build()
    }else{
        GeographyOuterClass.Polygon.getDefaultInstance()
    }
}


fun toPoly(polygon: GeographyOuterClass.Polygon?): Polygon? {
    return if(polygon != null && polygon.isInitialized ) {
        val vertices = polygon.verticesList.map { p -> XY(p.lng, p.lat) }
        Polygon( vertices );
    }else{
        null
    }
}
