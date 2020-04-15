package com.kgi.geograply_service.dao

import kgi.geography_api.GeographyOuterClass
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedJdbcTypes
import org.apache.ibatis.type.MappedTypes
import org.apache.ibatis.type.TypeHandler
import org.postgresql.geometric.PGpoint

import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import org.postgresql.geometric.PGpolygon;

@MappedTypes(GeographyOuterClass.AreaType::class)
@MappedJdbcTypes(value = [JdbcType.INTEGER, JdbcType.TINYINT], includeNullJdbcType = true)
class AreaTypeHandler : TypeHandler<GeographyOuterClass.AreaType> {

    override fun setParameter(ps: PreparedStatement, i: Int, parameter: GeographyOuterClass.AreaType?, jdbcType: JdbcType) {
           ps.setInt(i, parameter?.number?:0)

    }

    override fun getResult(rs: ResultSet, columnName: String): GeographyOuterClass.AreaType? {
        return toAreaType(rs.getInt(columnName) )
    }

    private fun toAreaType(i:Int): GeographyOuterClass.AreaType? {
       return  GeographyOuterClass.AreaType.forNumber(i)

    }

    override fun getResult(rs: ResultSet, columnIndex: Int): GeographyOuterClass.AreaType? {
        return toAreaType(rs.getInt(columnIndex))
    }

    override fun getResult(cs: CallableStatement, columnIndex: Int): GeographyOuterClass.AreaType? {
        return toAreaType(cs.getInt(columnIndex) )
    }
}
