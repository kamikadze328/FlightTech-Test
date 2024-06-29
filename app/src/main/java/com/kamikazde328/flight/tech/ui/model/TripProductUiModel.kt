package com.kamikazde328.flight.tech.ui.model

data class TripProductUiModel(
    val id: String,
    val quantity: Int,
    val title: String,
    val cost: Double?,
    val isPhysical: Boolean,
)