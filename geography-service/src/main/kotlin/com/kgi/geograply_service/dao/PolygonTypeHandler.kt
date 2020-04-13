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
        if (parameter != null) {
            val pgPoly = PGpolygon(parameter.vertices.map { PGpoint(it.x, it.y) }.toTypedArray())
            ps.setObject(i, pgPoly)
        } else {
            ps.setNull(i, JdbcType.OTHER.TYPE_CODE)
        }
    }

    override fun getResult(rs: ResultSet, columnName: String): Polygon? {
        return toPolygon(rs.getObject(columnName) as PGpolygon)
    }

    private fun toPolygon(pGpolygon: PGpolygon?): Polygon? {
        if (pGpolygon == null) return null
        return Polygon(pGpolygon.points.map { p -> XY(p.x, p.x) })
    }

    override fun getResult(rs: ResultSet, columnIndex: Int): Polygon? {
        return toPolygon(rs.getObject(columnIndex) as PGpolygon)
    }

    override fun getResult(cs: CallableStatement, columnIndex: Int): Polygon? {
        return toPolygon(cs.getObject(columnIndex) as PGpolygon)
    }
}
