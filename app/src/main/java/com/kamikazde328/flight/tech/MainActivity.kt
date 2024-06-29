package com.kamikazde328.flight.tech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.kamikazde328.flight.tech.ui.compose.MainScreen
import com.kamikazde328.flight.tech.ui.theme.FlightTechTestTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.onInit()

        setContent {
            FlightTechTestTheme {
                MainScreen()
            }
        }
    }
}