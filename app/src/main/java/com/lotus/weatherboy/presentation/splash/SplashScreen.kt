package com.lotus.weatherboy.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lotus.weatherboy.R
import com.lotus.weatherboy.ui.theme.DarkBlue
import com.lotus.weatherboy.ui.theme.DeepBlue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashScreenComplete: () -> Unit) {
    // State for controlling animations
    var isAnimationPlaying by remember { mutableStateOf(true) }

    // Sun animation
    val sunScale = remember { Animatable(0.5f) }
    val sunAlpha = remember { Animatable(0f) }

    // Rain animation
    val rainAlpha = remember { Animatable(0f) }
    val rainOffsetY = remember { Animatable(0f) }

    // Logo animation
    val logoScale = remember { Animatable(0.8f) }
    val logoAlpha = remember { Animatable(0f) }

    // Animation logic
    LaunchedEffect(isAnimationPlaying) {
        // Sun animation
        sunAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        sunScale.animateTo(
            targetValue = 1.2f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        sunScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )

        // Rain animation starts after sun is shown
        rainAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800)
        )
        repeat(2) {
            rainOffsetY.animateTo(
                targetValue = 100f,
                animationSpec = tween(durationMillis = 1000)
            )
            rainOffsetY.snapTo(0f)
        }

        // Logo animation starts after weather animation
        logoAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
        logoScale.animateTo(
            targetValue = 1.2f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        logoScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )

        // Complete splash screen after animation finishes
        delay(500)
        onSplashScreenComplete()
    }

    // Splash screen UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        DeepBlue,
                        DarkBlue
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Sun image
        Image(
            painter = painterResource(id = R.drawable.ic_sun),
            contentDescription = "Sun",
            modifier = Modifier
                .size(100.dp)
                .offset(x = (-50).dp, y = (-50).dp)
                .scale(sunScale.value)
                .alpha(sunAlpha.value)
        )

        // Rain drops
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = rainOffsetY.value.dp)
                .alpha(rainAlpha.value),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (i in 1..5) {
                Box(
                    modifier = Modifier
                        .size(8.dp, 24.dp)
                        .offset(x = (i * 15).dp)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Logo and app name
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(logoScale.value)
                .alpha(logoAlpha.value)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_weather_boy),
                contentDescription = "Weather Boy Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "WeatherBoy",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Your friendly weather companion",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}