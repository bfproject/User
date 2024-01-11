package com.architecture.core.state

/**
 * Contains the data state of the data layer
 */
sealed class DataState<out T : Any> {

    data class Success<out T : Any>(val data: T) : DataState<T>()

}
