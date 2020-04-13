package com.kgi.geograply_service.dao

import org.mybatis.dynamic.sql.SqlTable
import java.sql.JDBCType
import java.util.*

object AreaDynamicSqlSupport {
    object AreasTable : SqlTable("areas") {
        val id = column<String>("id", JDBCType.VARCHAR)
        val name = column<String>("name", JDBCType.VARCHAR)
        val polygon = column<Polygon>("polygon", JDBCType.OTHER, "com.kgi.geograply_service.dao.PolygonTypeHandler")
    }
}
