/*
 * Auto-generated file. Created by MyBatis Generator
 * Generation date: 2020-05-14T22:18:02.219915-07:00
 */
package com.kgi.geography_service.dao.mapper

import com.kgi.geography_service.dao.Polygon
import java.sql.JDBCType
import kgi.geography_api.GeographyOuterClass.AreaType
import org.mybatis.dynamic.sql.SqlTable

object AreaDynamicSqlSupport {
    object Area : SqlTable("areas") {
        val id = column<String>("id", JDBCType.VARCHAR)

        val name = column<String>("name", JDBCType.VARCHAR)

        val polygon = column<Polygon>("polygon", JDBCType.OTHER, "com.kgi.geography_service.dao.PolygonTypeHandler")

        val areaSqm = column<Double>("area_sqm", JDBCType.DOUBLE)

        val areaType = column<AreaType>("area_type", JDBCType.INTEGER, "com.kgi.geography_service.dao.AreaTypeHandler")
    }
}