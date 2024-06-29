package com.kamikazde328.flight.tech.ui.model

sealed class MainOneTimeEvent {
    class ShowError(val message: String) : MainOneTimeEvent()
}