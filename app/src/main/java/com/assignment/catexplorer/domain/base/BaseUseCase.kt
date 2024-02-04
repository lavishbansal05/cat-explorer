package com.assignment.catexplorer.domain.base

import com.assignment.catexplorer.domain.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<REQUEST, SUCCESS, ERROR> {

    private val coroutineDispatcher = Dispatchers.IO

    open suspend fun invoke(parameters: REQUEST): Response<SUCCESS, ERROR> {
        return withContext(coroutineDispatcher) {
            runCatching {
                execute(parameters)
            }.getOrElse {
                Response.UnknownError(it)
            }
        }
    }

    protected abstract suspend fun execute(parameters: REQUEST): Response<SUCCESS, ERROR>
}