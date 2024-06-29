package com.kamikazde328.flight.tech.ui.model

data class MainUiState(
    val isLoading: Boolean,
    val totalCost: Double,
    val trips: List<TripProductUiModel>,
) {
    companion object {
        val EMPTY = MainUiState(
            totalCost = 0.0,
            isLoading = false,
            trips = emptyList()
        )
    }
}