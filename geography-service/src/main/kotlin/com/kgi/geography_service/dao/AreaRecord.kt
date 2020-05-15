package com.kgi.geography_service.dao


data class XY( var x: Double, var y:Double )

data class Polygon( var vertices:List<XY> )
