package com.kgi.geograply_service.dao

import kgi.geography_api.GeographyOuterClass


data class AreaRecord (var id:String? = null, var name:String? = null, var polygon: Polygon? = null, var areaType: GeographyOuterClass.AreaType? = null)

data class XY( var x: Double, var y:Double )

data class Polygon( var vertices:List<XY> )
