package com.kgi.geography_service.dao

import com.kgi.geography_service.dao.mapper.AreaDynamicSqlSupport
import com.kgi.geography_service.dao.mapper.AreaDynamicSqlSupport.Area.id
import com.kgi.geography_service.dao.mapper.AreaDynamicSqlSupport.Area.name
import com.kgi.geography_service.dao.mapper.AreaDynamicSqlSupport.Area.polygon
import com.kgi.geography_service.dao.mapper.AreaMapper
import com.kgi.geography_service.dao.mapper.select
import com.kgi.geography_service.dao.model.AreaRecord
import org.mybatis.dynamic.sql.AbstractSingleValueCondition
import org.mybatis.dynamic.sql.SqlBuilder.isLikeCaseInsensitive
import org.mybatis.dynamic.sql.SqlBuilder.*
import org.mybatis.dynamic.sql.VisitableCondition
import org.mybatis.dynamic.sql.util.kotlin.*
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.*
import java.util.function.Supplier


fun AreaMapper.selectManyLike(text:String ): List<AreaRecord> {
    return this.select {
        where( name, isLikeCaseInsensitive("%${text}%"))
        orderBy( name )
    }
}


fun AreaMapper.delete(completer: DeleteCompleter) =
        deleteFrom(this::delete, AreaDynamicSqlSupport.Area, completer)

fun AreaMapper.deleteByPrimaryKey(id_: String) =
        this.delete {
            where(id, isEqualTo(id_))
        }



fun AreaMapper.selectManyContaining( xy:XY): List<AreaRecord>  {
    return this.select  {
        where( polygon, containsPoint(xy))
    }
}

fun containsPoint(xy: XY): VisitableCondition<Polygon> {
  return ContainmentCondition( xy )
}


class ContainmentCondition( val xy:XY ) : AbstractSingleValueCondition<Polygon> ( PolygonSupplier() ) {

    override fun renderCondition(columnName: String?, placeholder: String?): String {
       return columnName + " @> point '( ${xy.x},${xy.y})'"
    }

}


class PolygonSupplier( ) : Supplier<Polygon?> {
    override fun get(): Polygon? {
        return  null
    }
}
