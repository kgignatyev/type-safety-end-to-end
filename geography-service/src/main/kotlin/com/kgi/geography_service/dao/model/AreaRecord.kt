/*
 * Auto-generated file. Created by MyBatis Generator
 * Generation date: 2020-05-14T22:18:02.214699-07:00
 */
package com.kgi.geography_service.dao.model

import com.kgi.geography_service.dao.Polygon
import kgi.geography_api.GeographyOuterClass.AreaType

data class AreaRecord(
    var id: String? = null,
    var name: String? = null,
    var polygon: Polygon? = null,
    var areaSqm: Double? = null,
    var areaType: AreaType? = null
)