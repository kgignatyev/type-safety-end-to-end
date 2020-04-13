package com.kgi.geograply_service.dao

import com.kgi.geograply_service.GeographyApp
import com.kgi.geograply_service.impl.GeographySvcImpl
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [GeographyApp::class])
class AreasServiceTest {

    @Resource
    lateinit var geographySvcImpl: GeographySvcImpl

    @Test
    fun crudTest() {

        val timestamp = System.currentTimeMillis().toString()
        val polygon = Polygon( listOf(XY(1.0,1.0),XY(1.0,2.0), XY(2.0,2.0), XY(2.0,1.0)  ,XY(1.0,1.0)))
        val area = geographySvcImpl.create(AreaRecord("r$timestamp", "n$timestamp", polygon ))
        val foundArea = geographySvcImpl.getAreaByID( area.id!!)
        Assert.assertNotNull( foundArea )
        val areas = geographySvcImpl.findAreas(timestamp)
        Assert.assertEquals(1, areas.size)
        areas.forEach {
            println( it )
        }
        
        val poly2 = Polygon( listOf(XY(1.0,1.0),XY(1.0,3.0), XY(3.0,3.0), XY(3.0,1.0)  ,XY(1.0,1.0)))
        area.polygon = poly2
        geographySvcImpl.update( area )

    }


    @Test
    fun containmentTest() {
        val areas = geographySvcImpl.findAreasContaining(XY(1.5,1.5))
        Assert.assertTrue( "we should be able to find areas", areas.isNotEmpty())
        areas.forEach {
            println( it )
        }


        val areas2 = geographySvcImpl.findAreasContaining(XY(1.5,2.5))
        Assert.assertTrue( "we should be able to find areas", areas2.isNotEmpty())
        println(" found ${areas2.size}")
        areas2.forEach {
            println( it )
        }

    }
}
