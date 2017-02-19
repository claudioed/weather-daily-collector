package domain.temperature

/**
 * Created by claudio on 19/02/17.
 */
object TemperatureConverter {

    /**
     * From Fahrenheit to Celsius temperature converter
     */
    fun fromFahrenheitToCelsius(fahrenheit:Double):Double = (fahrenheit - 32 ) / 1.8

    /**
     * From Kelvin to Celsius temperature converter
     */
    fun fromKelvinToCelsius(kelvin:Double):Double = kelvin - 273

}