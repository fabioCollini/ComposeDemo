package com.composedemo

sealed class Lce<out T> {

    open val data: T? = null

    data class Success<out T>(override val data: T) : Lce<T>()

    data class Error(val throwable: Throwable) : Lce<Nothing>()

    object Loading : Lce<Nothing>()
}
