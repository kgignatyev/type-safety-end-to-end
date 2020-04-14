package com.kgi.geograply_service.dao

import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedJdbcTypes
import org.apache.ibatis.type.MappedTypes
import org.apache.ibatis.type.TypeHandler
import org.postgresql.geometric.PGpoint

import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import org.postgresql.geometric.PGpolygon;

@MappedTypes(Polygon::class)
@MappedJdbcTypes(value = [JdbcType.OTHER, JdbcType.JAVA_OBJECT], includeNullJdbcType = true)
class PolygonTypeHandler : TypeHandler<Polygon> {

    override fun setParameter(ps: PreparedStatement, i: Int, parameter: Polygon?, jdbcType: JdbcType) {
        if (parameter != null && parameter.vertices.isNotEmpty()) {
            val pgPoly = PGpolygon(parameter.vertices.map { PGpoint(it.x, it.y) }.toTypedArray())
            ps.setObject(i, pgPoly)
        } else {
            //we must not get here it is invalid
            ps.setObject(i, PGpolygon(arrayOf(PGpoint(0.1,0.1),PGpoint(0.1,0.2),PGpoint(0.2,0.2),PGpoint(0.1,0.2),PGpoint(0.1,0.1))))
        }
    }

    override fun getResult(rs: ResultSet, columnName: String): Polygon? {
        return toPolygon(rs.getObject(columnName) )
    }

    private fun toPolygon(pGpolygon: Any): Polygon? {
       return  when( pGpolygon ){
            is PGpolygon -> Polygon(pGpolygon.points.map { p -> XY(p.x, p.y) })
            else ->Polygon(listOf())
        }

    }

    override fun getResult(rs: ResultSet, columnIndex: Int): Polygon? {
        return toPolygon(rs.getObject(columnIndex))
    }

    override fun getResult(cs: CallableStatement, columnIndex: Int): Polygon? {
        return toPolygon(cs.getObject(columnIndex) )
    }
}
