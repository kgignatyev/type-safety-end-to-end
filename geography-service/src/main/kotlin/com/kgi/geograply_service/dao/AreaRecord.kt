package com.kgi.geograply_service.dao


data class AreaRecord (var id:String? = null, var name:String? = null, var polygon: Polygon? = null)

data class XY( var x: Double, var y:Double )

data class Polygon( var vertices:List<XY> )
