package com.lotus.weatherboy.data.remote

import com.lotus.weatherboy.data.remote.dto.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): WeatherResponse
}