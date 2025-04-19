package com.lotus.weatherboy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lotus.weatherboy.domain.model.WeatherInfo
import com.lotus.weatherboy.domain.repository.WeatherRepository
import com.lotus.weatherboy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Initial)
    val state: StateFlow<WeatherState> = _state

    private var currentCity = "Los Angeles" // Default city changed to Los Angeles

    init {
        getWeatherForCity(currentCity)
    }

    fun getWeatherForCity(city: String) {
        currentCity = city
        repository.getWeatherByCity(city).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = WeatherState.Success(result.data!!)
                }
                is Resource.Error -> {
                    _state.value = WeatherState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = WeatherState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getWeatherForLocation(latitude: Double, longitude: Double) {
        repository.getWeatherByLocation(latitude, longitude).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = WeatherState.Success(result.data!!)
                }
                is Resource.Error -> {
                    _state.value = WeatherState.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = WeatherState.Loading
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refreshWeather() {
        getWeatherForCity(currentCity)
    }
}

sealed class WeatherState {
    object Initial : WeatherState()
    object Loading : WeatherState()
    data class Success(val weatherInfo: WeatherInfo) : WeatherState()
    data class Error(val message: String) : WeatherState()
}