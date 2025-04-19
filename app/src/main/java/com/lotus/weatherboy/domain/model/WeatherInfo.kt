package com.lotus.weatherboy.domain.model

data class WeatherInfo(
    val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val iconCode: String,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)