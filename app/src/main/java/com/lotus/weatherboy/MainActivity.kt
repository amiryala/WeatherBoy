package com.lotus.weatherboy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.lotus.weatherboy.presentation.WeatherScreen
import com.lotus.weatherboy.presentation.WeatherViewModel
import com.lotus.weatherboy.presentation.splash.SplashScreen
import com.lotus.weatherboy.ui.theme.WeatherBoyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherBoyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showSplashScreen by remember { mutableStateOf(true) }

                    if (showSplashScreen) {
                        SplashScreen(
                            onSplashScreenComplete = {
                                showSplashScreen = false
                            }
                        )
                    } else {
                        WeatherScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}