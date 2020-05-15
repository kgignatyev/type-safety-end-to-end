package com.kgi.geography_service

import com.kgi.geography_service.converters.AreaToDAO
import com.kgi.geography_service.converters.DAOToArea
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ConversionServiceFactoryBean
import org.springframework.core.convert.ConversionService
import org.springframework.scheduling.annotation.EnableAsync
import java.util.HashSet

@SpringBootApplication
@Configuration
@EnableAsync(proxyTargetClass = true)
@EnableCaching(proxyTargetClass = true)
@MapperScan(basePackages = [
    "com.kgi.geography_service.dao",
    "com.kgi.geography_service.dao.mapper"
])
class GeographyApp {


    @Bean
    fun conversionService(): ConversionService {
            val converters = HashSet<Any>()
            converters.add(AreaToDAO())
            converters.add(DAOToArea())
            val bean = ConversionServiceFactoryBean()
            bean.setConverters(converters)
            bean.afterPropertiesSet()
            return bean.`object`!!
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(GeographyApp::class.java, *args)
        }
    }
}
