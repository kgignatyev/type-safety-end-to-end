/*
 * Auto-generated file. Created by MyBatis Generator
 * Generation date: 2020-08-04T21:34:12.064033-07:00
 */
package com.kgi.geography_service.dao.mapper

import com.kgi.geography_service.dao.mapper.AreaDynamicSqlSupport.Area
import com.kgi.geography_service.dao.mapper.AreaDynamicSqlSupport.Area.areaSqm
import com.kgi.geography_service.dao.mapper.AreaDynamicSqlSupport.Area.areaType
import com.kgi.geography_service.dao.mapper.AreaDynamicSqlSupport.Area.id
import com.kgi.geography_service.dao.mapper.AreaDynamicSqlSupport.Area.name
import com.kgi.geography_service.dao.mapper.AreaDynamicSqlSupport.Area.polygon
import com.kgi.geography_service.dao.model.AreaRecord
import org.mybatis.dynamic.sql.SqlBuilder.isEqualTo
import org.mybatis.dynamic.sql.util.kotlin.*
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.*

fun AreaMapper.count(completer: CountCompleter) =
    countFrom(this::count, Area, completer)

fun AreaMapper.delete(completer: DeleteCompleter) =
    deleteFrom(this::delete, Area, completer)

fun AreaMapper.deleteByPrimaryKey(id_: String) =
    delete {
        where(id, isEqualTo(id_))
    }

fun AreaMapper.insert(record: AreaRecord) =
    insert(this::insert, record, Area) {
        map(id).toProperty("id")
        map(name).toProperty("name")
        map(polygon).toProperty("polygon")
        map(areaSqm).toProperty("areaSqm")
        map(areaType).toProperty("areaType")
    }

fun AreaMapper.insertMultiple(records: Collection<AreaRecord>) =
    insertMultiple(this::insertMultiple, records, Area) {
        map(id).toProperty("id")
        map(name).toProperty("name")
        map(polygon).toProperty("polygon")
        map(areaSqm).toProperty("areaSqm")
        map(areaType).toProperty("areaType")
    }

fun AreaMapper.insertMultiple(vararg records: AreaRecord) =
    insertMultiple(records.toList())

fun AreaMapper.insertSelective(record: AreaRecord) =
    insert(this::insert, record, Area) {
        map(id).toPropertyWhenPresent("id", record::id)
        map(name).toPropertyWhenPresent("name", record::name)
        map(polygon).toPropertyWhenPresent("polygon", record::polygon)
        map(areaSqm).toPropertyWhenPresent("areaSqm", record::areaSqm)
        map(areaType).toPropertyWhenPresent("areaType", record::areaType)
    }

private val columnList = listOf(id, name, polygon, areaSqm, areaType)

fun AreaMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, Area, completer)

fun AreaMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, Area, completer)

fun AreaMapper.selectDistinct(completer: SelectCompleter) =
    selectDistinct(this::selectMany, columnList, Area, completer)

fun AreaMapper.selectByPrimaryKey(id_: String) =
    selectOne {
        where(id, isEqualTo(id_))
    }

fun AreaMapper.update(completer: UpdateCompleter) =
    update(this::update, Area, completer)

fun KotlinUpdateBuilder.updateAllColumns(record: AreaRecord) =
    apply {
        set(id).equalTo(record::id)
        set(name).equalTo(record::name)
        set(polygon).equalTo(record::polygon)
        set(areaSqm).equalTo(record::areaSqm)
        set(areaType).equalTo(record::areaType)
    }

fun KotlinUpdateBuilder.updateSelectiveColumns(record: AreaRecord) =
    apply {
        set(id).equalToWhenPresent(record::id)
        set(name).equalToWhenPresent(record::name)
        set(polygon).equalToWhenPresent(record::polygon)
        set(areaSqm).equalToWhenPresent(record::areaSqm)
        set(areaType).equalToWhenPresent(record::areaType)
    }

fun AreaMapper.updateByPrimaryKey(record: AreaRecord) =
    update {
        set(name).equalTo(record::name)
        set(polygon).equalTo(record::polygon)
        set(areaSqm).equalTo(record::areaSqm)
        set(areaType).equalTo(record::areaType)
        where(id, isEqualTo(record::id))
    }

fun AreaMapper.updateByPrimaryKeySelective(record: AreaRecord) =
    update {
        set(name).equalToWhenPresent(record::name)
        set(polygon).equalToWhenPresent(record::polygon)
        set(areaSqm).equalToWhenPresent(record::areaSqm)
        set(areaType).equalToWhenPresent(record::areaType)
        where(id, isEqualTo(record::id))
    }