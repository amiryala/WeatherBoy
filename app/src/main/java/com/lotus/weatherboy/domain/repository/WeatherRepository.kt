package com.lotus.weatherboy.domain.repository

import com.lotus.weatherboy.domain.model.WeatherInfo
import com.lotus.weatherboy.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherByCity(cityName: String): Flow<Resource<WeatherInfo>>
    fun getWeatherByLocation(latitude: Double, longitude: Double): Flow<Resource<WeatherInfo>>
}