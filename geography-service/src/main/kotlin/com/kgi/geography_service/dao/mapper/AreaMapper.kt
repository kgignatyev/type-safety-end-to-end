/*
 * Auto-generated file. Created by MyBatis Generator
 * Generation date: 2020-08-04T21:34:12.058593-07:00
 */
package com.kgi.geography_service.dao.mapper

import com.kgi.geography_service.dao.AreaTypeHandler
import com.kgi.geography_service.dao.PolygonTypeHandler
import com.kgi.geography_service.dao.model.AreaRecord
import org.apache.ibatis.annotations.DeleteProvider
import org.apache.ibatis.annotations.InsertProvider
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.annotations.UpdateProvider
import org.apache.ibatis.type.JdbcType
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider
import org.mybatis.dynamic.sql.util.SqlProviderAdapter

@Mapper
interface AreaMapper {
    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    fun count(selectStatement: SelectStatementProvider): Long

    @DeleteProvider(type=SqlProviderAdapter::class, method="delete")
    fun delete(deleteStatement: DeleteStatementProvider): Int

    @InsertProvider(type=SqlProviderAdapter::class, method="insert")
    fun insert(insertStatement: InsertStatementProvider<AreaRecord>): Int

    @InsertProvider(type=SqlProviderAdapter::class, method="insertMultiple")
    fun insertMultiple(multipleInsertStatement: MultiRowInsertStatementProvider<AreaRecord>): Int

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @ResultMap("AreaRecordResult")
    fun selectOne(selectStatement: SelectStatementProvider): AreaRecord?

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @Results(id="AreaRecordResult", value = [
        Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        Result(column="polygon", property="polygon", typeHandler=PolygonTypeHandler::class, jdbcType=JdbcType.OTHER),
        Result(column="area_sqm", property="areaSqm", jdbcType=JdbcType.DOUBLE),
        Result(column="area_type", property="areaType", typeHandler=AreaTypeHandler::class, jdbcType=JdbcType.INTEGER)
    ])
    fun selectMany(selectStatement: SelectStatementProvider): List<AreaRecord>

    @UpdateProvider(type=SqlProviderAdapter::class, method="update")
    fun update(updateStatement: UpdateStatementProvider): Int
}