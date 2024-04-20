package utils

sealed class UiEvents {
    data class SnackbarEvent(val message: String) : UiEvents()
}