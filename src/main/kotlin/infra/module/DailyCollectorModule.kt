package infra.module

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.name.Names
import domain.repository.CassandraDataRepository
import domain.repository.DataRepository
import java.util.*

/**
 * Daily collector module
 * @author Claudio E. de Oliveira<claudioed.oliveira@gmail.com>
 *
 */
class DailyCollectorModule : AbstractModule() {

    override fun configure() {
        install(RabbitModule())
        install(CassandraModule())
        bindRepository()
        bindSenderId()
    }

    private fun bindRepository():Unit{
        bind(DataRepository::class.java).to(CassandraDataRepository::class.java)
    }

    private fun bindSenderId():Unit{
        bind(String::class.java).annotatedWith(Names.named("sender_id")).toInstance(UUID.randomUUID().toString())
    }

    @Provides
    fun mapper(): ObjectMapper = ObjectMapper().registerKotlinModule().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

}