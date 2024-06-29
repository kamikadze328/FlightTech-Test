package com.kamikazde328.flight.tech

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamikazde328.flight.tech.data.sky.depot.TripRepository
import com.kamikazde328.flight.tech.data.sky.depot.model.response.TripsProduct
import com.kamikazde328.flight.tech.ui.model.MainOneTimeEvent
import com.kamikazde328.flight.tech.ui.model.MainUiState
import com.kamikazde328.flight.tech.ui.model.TripProductUiModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _uiState = MutableStateFlow(MainUiState.EMPTY)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _oneTimeEvent: MutableStateFlow<List<MainOneTimeEvent>> =
        MutableStateFlow(emptyList())
    val oneTimeEvent: StateFlow<List<MainOneTimeEvent>> = _oneTimeEvent.asStateFlow()

    private val tripRepository = TripRepository.getInstance()


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Coroutine Exception: ${exception.message}")
        onLoadTripsFailed(exception)
    }

    fun fetchData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch(exceptionHandler + Dispatchers.IO) {
            val trips = tripRepository.showTrips()
            onLoadTrips(trips)
        }
    }

    private fun onLoadTrips(trips: List<TripsProduct>) {
        val tripsProductsUi = trips.mapToUi()
        val totalCost = tripsProductsUi.calculateTotalCost()

        _uiState.value = _uiState.value.copy(
            isLoading = false,
            totalCost = totalCost,
            trips = tripsProductsUi
        )
    }

    private fun List<TripProductUiModel>.calculateTotalCost(): Double {
        return sumOf { (it.cost ?: 0.0) * it.quantity }
    }

    private fun List<TripsProduct>.mapToUi(): List<TripProductUiModel> {
        return mapNotNull { it.mapToUi() }
    }

    private fun TripsProduct.mapToUi(): TripProductUiModel? {
        return TripProductUiModel(
            id = id ?: return null,
            quantity = quantity?.toIntOrNull() ?: 0,
            title = title.orEmpty(),
            cost = cost?.toDoubleOrNull(),
            isPhysical = physical.equals("y", true),
        )
    }

    private fun onLoadTripsFailed(throwable: Throwable?) {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
        )
        val message = throwable?.message ?: "Unknown error"
        _oneTimeEvent.value = listOf(MainOneTimeEvent.ShowError(message))
    }
}