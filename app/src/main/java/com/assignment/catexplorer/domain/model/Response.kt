package com.assignment.catexplorer.domain.model

sealed class Response<out SUCCESS, out ERROR> {
    data class Success<SUCCESS>(val body: SUCCESS) : Response<SUCCESS, Nothing>()

    data class Error<ERROR>(val error: ERROR) : Response<Nothing, ERROR>()
    data class UnknownError(val error: Throwable? = null, val code: Int? = null) :
        Response<Nothing, Nothing>()
}