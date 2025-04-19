package com.lotus.weatherboy.util

import com.lotus.weatherboy.BuildConfig

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY
    const val ICON_URL = "https://openweathermap.org/img/wn/%s@4x.png"
}