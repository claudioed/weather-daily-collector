package domain.repository

import com.datastax.driver.core.Cluster
import com.google.inject.Inject
import com.google.inject.Singleton
import com.google.inject.name.Named
import domain.service.Data
import domain.temperature.TemperatureConverter
import java.time.LocalDate
import java.util.*

/**
 * Cassandra Data repository
 * @author Claudio E. de Oliveira<claudioed.oliveira@gmail.com>
 *
 */
@Singleton
class CassandraDataRepository : DataRepository {

    @Inject
    private lateinit var cluster: Cluster

    @Inject @Named("sender_id")
    private lateinit var senderId: String

    val insertQuery: String = "insert into temperature (id,sender_id,day,month,year,temperature,city_id,city_name) values (?,?,?,?,?,?,?,?)"

    override fun insert(data: Data) {
        val session = cluster.connect("weather_daily")
        val preparedStatement = session.prepare(this.insertQuery)
        val now = LocalDate.now()
        val celsius = data.main.temp.toDouble().to { TemperatureConverter.fromKelvinToCelsius(it) }
        session.execute(preparedStatement.bind(UUID.randomUUID().toString(), senderId, now.dayOfMonth.toString(), now.monthValue.toString(), now.year.toString(), celsius, data.id, data.name))
        session.close()
    }

    private fun Double.to(converter: (Double) -> Double): Double = converter(toDouble())

}