package infra.starter

import com.google.inject.Guice
import domain.service.WeatherDailyConsumer
import infra.module.DailyCollectorModule

/**
 * Created by claudio on 18/02/17.
 */
class DailyStarter {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val injector = Guice.createInjector(DailyCollectorModule())
            val consumer = injector.getInstance(WeatherDailyConsumer::class.java)
            consumer.consumer()
            println("Daily stats listening weather queue")
        }
    }
}