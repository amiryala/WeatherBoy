package com.lotus.weatherboy.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.lotus.weatherboy.domain.model.WeatherInfo
import com.lotus.weatherboy.ui.theme.DarkBlue
import com.lotus.weatherboy.ui.theme.DeepBlue
import com.lotus.weatherboy.util.Constants
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel
) {
    val state = viewModel.state.collectAsState().value
    var searchQuery by remember { mutableStateOf("") }
    var isCelsius by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DeepBlue,
                        DarkBlue
                    )
                )
            )
            // Add padding to the top to avoid overlapping with status bar
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        // Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Basic TextField
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search city") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchQuery.isNotBlank()) {
                            viewModel.getWeatherForCity(searchQuery)
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Search Button
            Button(
                onClick = {
                    if (searchQuery.isNotBlank()) {
                        viewModel.getWeatherForCity(searchQuery)
                    }
                }
            ) {
                Text("Search")
            }
        }

        // Unit Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "°C",
                color = if (isCelsius) Color.White else Color.White.copy(alpha = 0.5f),
                fontWeight = if (isCelsius) FontWeight.Bold else FontWeight.Normal
            )
            Switch(
                checked = !isCelsius,
                onCheckedChange = { isCelsius = !it }
            )
            Text(
                text = "°F",
                color = if (!isCelsius) Color.White else Color.White.copy(alpha = 0.5f),
                fontWeight = if (!isCelsius) FontWeight.Bold else FontWeight.Normal
            )
        }

        // Content based on state
        when (state) {
            is WeatherState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
            is WeatherState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
            is WeatherState.Success -> {
                WeatherContent(weatherInfo = state.weatherInfo, isCelsius = isCelsius)
            }
            is WeatherState.Initial -> {
                // Initial state, show nothing or a welcome message
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Enter a city to see the weather",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherContent(weatherInfo: WeatherInfo, isCelsius: Boolean) {
    // Function to convert Celsius to Fahrenheit
    fun celsiusToFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }

    // Get temperature in the current unit
    val temperature = if (isCelsius)
        weatherInfo.temperature
    else
        celsiusToFahrenheit(weatherInfo.temperature)

    val feelsLike = if (isCelsius)
        weatherInfo.feelsLike
    else
        celsiusToFahrenheit(weatherInfo.feelsLike)

    // Current unit symbol
    val unitSymbol = if (isCelsius) "°C" else "°F"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // City and Country
        Text(
            text = "${weatherInfo.cityName}, ${weatherInfo.country}",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weather Icon
        val iconUrl = String.format(Constants.ICON_URL, weatherInfo.iconCode)
        Image(
            painter = rememberAsyncImagePainter(iconUrl),
            contentDescription = "Weather icon",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Temperature
        Text(
            text = "${temperature.toInt()}$unitSymbol",
            color = Color.White,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        // Weather Description
        Text(
            text = weatherInfo.description,
            color = Color.White,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Weather Details
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherDetailItem(
                title = "Feels Like",
                value = "${feelsLike.toInt()}$unitSymbol"
            )
            WeatherDetailItem(
                title = "Humidity",
                value = "${weatherInfo.humidity}%"
            )
            WeatherDetailItem(
                title = "Wind",
                value = "${weatherInfo.windSpeed} m/s"
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sunrise and Sunset
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            WeatherDetailItem(
                title = "Sunrise",
                value = dateFormat.format(Date(weatherInfo.sunrise * 1000))
            )
            WeatherDetailItem(
                title = "Sunset",
                value = dateFormat.format(Date(weatherInfo.sunset * 1000))
            )
        }
    }
}

@Composable
fun WeatherDetailItem(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.2f))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// Helper function to add top padding that accounts for status bar
// This is a modifier extension function
fun Modifier.statusBarsPadding(): Modifier = this.then(Modifier.padding(top = 24.dp))