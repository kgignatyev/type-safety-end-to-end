package com.kgi.geograply_service.dao

import com.kgi.geograply_service.dao.AreaDynamicSqlSupport.AreasTable.area_type
import com.kgi.geograply_service.dao.AreaDynamicSqlSupport.AreasTable.id
import com.kgi.geograply_service.dao.AreaDynamicSqlSupport.AreasTable.name
import com.kgi.geograply_service.dao.AreaDynamicSqlSupport.AreasTable.polygon
import kgi.geography_api.GeographyOuterClass
import org.mybatis.dynamic.sql.AbstractSingleValueCondition
import org.mybatis.dynamic.sql.SqlBuilder.*
import org.mybatis.dynamic.sql.VisitableCondition
import org.mybatis.dynamic.sql.util.kotlin.*
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.*
import java.util.function.Supplier

private val columnList = listOf(id, name, polygon, area_type )

fun AreaMapper.insert(record: AreaRecord) =
        insert(this::insert, record, AreaDynamicSqlSupport.AreasTable) {
            map(id).toProperty("id")
            map(name).toProperty("name")
            map(polygon).toProperty("polygon")
            map(area_type).toProperty("areaType")
        }


fun AreaMapper.update(completer: UpdateCompleter) =
        update(this::update, AreaDynamicSqlSupport.AreasTable, completer)




fun AreaMapper.delete(completer: DeleteCompleter) =
        deleteFrom(this::delete, AreaDynamicSqlSupport.AreasTable, completer)

fun AreaMapper.deleteByPrimaryKey(id_: String) =
        delete {
            where(id, isEqualTo(id_))
        }


fun AreaMapper.updateByPrimaryKey(record: AreaRecord) =
        update {
            set(name).equalTo(record::name)
            set(area_type).equalTo(record::areaType)
            set(polygon).equalTo(record::polygon)
            where(id, isEqualTo(record::id))
        }




fun AreaMapper.select(completer: SelectCompleter): List<AreaRecord> =
        selectList(this::selectMany, columnList, AreaDynamicSqlSupport.AreasTable, completer)

fun AreaMapper.selectManyLike(text:String ): List<AreaRecord> {
    return select {
        where( name, isLikeCaseInsensitive("%${text}%"))
        orderBy( name )
    }
}


fun AreaMapper.selectManyContaining( xy:XY): List<AreaRecord>  {
    return select {
        where( polygon, containsPoint(xy))
    }
}

fun containsPoint(xy: XY): VisitableCondition<Polygon> {
  return ContainmentCondition( xy )
}

fun AreaMapper.selectOne(completer: SelectCompleter) =
        selectOne(this::selectOne, columnList, AreaDynamicSqlSupport.AreasTable, completer)

fun AreaMapper.selectByPrimaryKey(id_: String) =
        selectOne {
            where(id, isEqualTo(id_))
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
