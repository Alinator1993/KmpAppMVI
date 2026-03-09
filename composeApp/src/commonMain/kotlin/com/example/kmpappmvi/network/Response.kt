package com.example.kmpappmvi.network

sealed class Response<out T> {
    data class Success<T>(val result : T) : Response<T>()
    data class Error<Nothing>(val message : String) : Response<Nothing>()
}