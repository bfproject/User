package com.architecture.core.state

/**
 * Contains the state of the view
 */
sealed class UiState<out T : Any> {

    object Loading : UiState<Nothing>()

    data class Success<T : Any>(val data: T) : UiState<T>()

}
