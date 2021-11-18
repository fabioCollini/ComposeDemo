package com.composedemo.utils

sealed class Lce<out T> {

    open val data: T? = null

    data class Success<out T>(override val data: T) : Lce<T>()

    data class Error(val throwable: Throwable) : Lce<Nothing>()

    data class Loading<out T>(override val data: T?) : Lce<T>()
}
