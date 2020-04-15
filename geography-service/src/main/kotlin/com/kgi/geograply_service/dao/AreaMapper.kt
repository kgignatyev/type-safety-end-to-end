package com.kgi.geograply_service.dao

import org.apache.ibatis.annotations.*
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider
import org.mybatis.dynamic.sql.util.SqlProviderAdapter


interface AreaMapper {

    @InsertProvider(type = SqlProviderAdapter::class, method = "insert")
    fun insert(insertStatement: InsertStatementProvider<AreaRecord>): Int

    @InsertProvider(type = SqlProviderAdapter::class, method = "insertMultiple")
    fun insertMultiple(multipleInsertStatement: MultiRowInsertStatementProvider<AreaRecord>): Int

    @UpdateProvider(type = SqlProviderAdapter::class, method = "update")
    fun update(updateStatement: UpdateStatementProvider): Int

    @SelectProvider(type = SqlProviderAdapter::class, method = "select")
    @Results(
        id = "AreaRecordResult", value = [
            Result(column = "id", property = "id"),
            Result(column = "name", property = "name"),
            Result(column = "polygon", property = "polygon", typeHandler = PolygonTypeHandler::class),
            Result(column = "area_type", property = "areaType", typeHandler = AreaTypeHandler::class)
        ]
    )
    fun selectMany(selectStatement: SelectStatementProvider): List<AreaRecord>

    @SelectProvider(type = SqlProviderAdapter::class, method = "select")
    @ResultMap("AreaRecordResult")
    fun selectOne(selectStatement: SelectStatementProvider): AreaRecord?

    @DeleteProvider(type = SqlProviderAdapter::class, method = "delete")
    fun delete(deleteStatement: DeleteStatementProvider): Int

    @SelectProvider(type = SqlProviderAdapter::class, method = "select")
    fun count(selectStatement: SelectStatementProvider): Long
}
