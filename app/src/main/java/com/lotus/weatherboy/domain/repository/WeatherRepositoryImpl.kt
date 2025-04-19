package com.lotus.weatherboy.data.repository

import com.lotus.weatherboy.data.remote.WeatherApi
import com.lotus.weatherboy.data.remote.dto.WeatherResponse
import com.lotus.weatherboy.domain.model.WeatherInfo
import com.lotus.weatherboy.domain.repository.WeatherRepository
import com.lotus.weatherboy.util.Constants
import com.lotus.weatherboy.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override fun getWeatherByCity(cityName: String): Flow<Resource<WeatherInfo>> = flow {
        emit(Resource.Loading())
        try {
            val weatherResponse = api.getWeatherByCity(
                cityName = cityName,
                apiKey = Constants.WEATHER_API_KEY
            )
            emit(Resource.Success(mapToWeatherInfo(weatherResponse)))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "An unexpected error occurred: ${e.localizedMessage ?: "Unknown HTTP error"}"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error(message = "An unexpected error occurred: ${e.localizedMessage ?: "Unknown error"}"))
        }
    }

    override fun getWeatherByLocation(
        latitude: Double,
        longitude: Double
    ): Flow<Resource<WeatherInfo>> = flow {
        emit(Resource.Loading())
        try {
            val weatherResponse = api.getWeatherByLocation(
                latitude = latitude,
                longitude = longitude,
                apiKey = Constants.WEATHER_API_KEY
            )
            emit(Resource.Success(mapToWeatherInfo(weatherResponse)))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "An unexpected error occurred: ${e.localizedMessage ?: "Unknown HTTP error"}"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error(message = "An unexpected error occurred: ${e.localizedMessage ?: "Unknown error"}"))
        }
    }

    private fun mapToWeatherInfo(weatherResponse: WeatherResponse): WeatherInfo {
        val weather = weatherResponse.weather.firstOrNull()
        return WeatherInfo(
            cityName = weatherResponse.name,
            temperature = weatherResponse.main.temp,
            feelsLike = weatherResponse.main.feelsLike,
            humidity = weatherResponse.main.humidity,
            windSpeed = weatherResponse.wind.speed,
            description = weather?.description?.capitalize() ?: "",
            iconCode = weather?.icon ?: "01d",
            country = weatherResponse.sys.country,
            sunrise = weatherResponse.sys.sunrise,
            sunset = weatherResponse.sys.sunset
        )
    }

    private fun String.capitalize(): String {
        return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}