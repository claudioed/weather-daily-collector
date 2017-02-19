package domain.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import com.google.inject.Singleton
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Connection
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import domain.repository.DataRepository
import java.nio.charset.Charset

/**
 * Daily consumer
 * @author Claudio E. de Oliveira<claudioed.oliveira@gmail.com>
 *
 */
@Singleton
class WeatherDailyConsumer{

    @Inject
    private lateinit var connection:Connection

    @Inject
    private lateinit var mapper:ObjectMapper

    @Inject
    private lateinit var repository:DataRepository

    fun consumer(): Unit {
        val channel = connection.createChannel()
        channel.basicConsume("weather",true ,object : DefaultConsumer(channel) {
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray) {
                val message = kotlin.text.String(body, Charset.defaultCharset())
                val data = mapper.readValue(message, Data::class.java)
                repository.insert(data)
            }
        })
    }

}

data class Data(val main: Temperature, val id: String, val name: String, val wind: Wind, val clouds: Cloud)

data class Temperature(val temp: String, val pressure: String, val humidity: String, val temp_min: String, val temp_max: String)

data class Wind(val speed: String, val deg: String)

data class Cloud(val all: String)
