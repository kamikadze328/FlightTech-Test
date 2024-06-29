package com.kamikazde328.flight.tech

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamikazde328.flight.tech.data.sky.depot.TripRepository
import com.kamikazde328.flight.tech.data.sky.depot.model.response.Trips
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState.EMPTY)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _oneTimeEvent: MutableStateFlow<List<MainOneTimeEvent>> =
        MutableStateFlow(emptyList())
    val oneTimeEvent: StateFlow<List<MainOneTimeEvent>> = _oneTimeEvent.asStateFlow()

    private val tripRepository = TripRepository.getInstance()


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("MainViewModel", "Exception: ${exception.message}")
        onLoadTripsFailed(exception)
    }

    fun onInit() {
        _uiState.value = uiState.value.copy(isLoading = true)
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val result = tripRepository.showTrips()
            if (result.isSuccess) {
                onLoadTrips(result.getOrNull())
            } else {
                onLoadTripsFailed(result.exceptionOrNull())
            }
        }
    }

    private fun onLoadTrips(result: Trips?) {
        _uiState.value = uiState.value.copy(
            isLoading = false,
            result = result.toString(),
        )
    }

    private fun onLoadTripsFailed(throwable: Throwable?) {
        _uiState.value = uiState.value.copy(
            isLoading = false,
        )
        val message = throwable?.message ?: "Unknown error"
        _oneTimeEvent.value = listOf(MainOneTimeEvent.ShowError(message))
    }

}

data class MainUiState(
    val isLoading: Boolean,
    val result: String,
    val trips: List<TripUiModel>,
) {
    companion object {
        val EMPTY = MainUiState(
            isLoading = false,
            result = "",
            trips = emptyList()
        )
    }
}

data class TripUiModel(
    val id: String,
    val company: String,
)

sealed class MainOneTimeEvent {
    class ShowError(val message: String) : MainOneTimeEvent()
}