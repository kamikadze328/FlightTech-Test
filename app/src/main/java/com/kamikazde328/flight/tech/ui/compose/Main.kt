package com.kamikazde328.flight.tech.ui.compose

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kamikazde328.flight.tech.MainOneTimeEvent
import com.kamikazde328.flight.tech.MainViewModel
import com.kamikazde328.flight.tech.ui.theme.FlightTechTestTheme

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val oneTimeEvent by viewModel.oneTimeEvent.collectAsState()

    OnOneTimeEvents(oneTimeEvent)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Greeting(
            name = uiState.result,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun OnOneTimeEvents(oneTimeEvents: List<MainOneTimeEvent>) {
    oneTimeEvents.forEach {
        when (it) {
            is MainOneTimeEvent.ShowError -> {
                Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Result is $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlightTechTestTheme {
        Greeting("Android")
    }
}