package com.samfonsec.fuzecs.data.api

sealed class Status<out T : Any> {
    class Success<out T : Any>(val data: T?) : Status<T>()
    class Error(val message: String) : Status<Nothing>()
    object Loading : Status<Nothing>()
}