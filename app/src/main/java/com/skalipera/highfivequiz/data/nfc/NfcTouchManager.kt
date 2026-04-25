package com.skalipera.highfivequiz.data.nfc

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class NfcTouchState(
    val eventId: Long = 0L,
    val lastAction: String = "",
    val lastTouchAtMillis: Long = 0L
)

object NfcTouchManager {
    private val _state = MutableStateFlow(NfcTouchState())
    val state: StateFlow<NfcTouchState> = _state.asStateFlow()

    fun recordTouch(action: String) {
        _state.value = NfcTouchState(
            eventId = _state.value.eventId + 1,
            lastAction = action,
            lastTouchAtMillis = System.currentTimeMillis()
        )
    }
}
