package com.kgi.geography_service.impl

import com.kgi.geography_service.dao.*
import com.kgi.geography_service.dao.mapper.AreaMapper
import com.kgi.geography_service.dao.mapper.insert
import com.kgi.geography_service.dao.mapper.selectByPrimaryKey
import com.kgi.geography_service.dao.mapper.updateByPrimaryKey
import com.kgi.geography_service.dao.model.AreaRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.annotation.Resource


@Service
@Transactional
class GeographySvcImpl {

    @Resource
    lateinit var areaMapper: AreaMapper

    fun findAreas(text: String):List<AreaRecord> {
        return areaMapper.selectManyLike( text )
    }

    fun create( a:AreaRecord ):AreaRecord {
        a.id = UUID.randomUUID().toString()
        areaMapper.insert( a )
        return a
    }

    fun findAreasContaining(xy: XY): List<AreaRecord> {
        return areaMapper.selectManyContaining( xy )
    }

    fun update(area: AreaRecord): Int {
        return areaMapper.updateByPrimaryKey( area )
    }

    fun getAreaByID(id: String): AreaRecord {
        return areaMapper.selectByPrimaryKey(id )!!
    }

    fun deleteAreaByID(id: String) {
        areaMapper.deleteByPrimaryKey(id)
    }

}
