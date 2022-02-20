package com.example.moviesdb.common

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

fun <T : Any> Result<T>.whenSuccess(block: (data: T) -> Unit): Result<T> {
    if (this is Result.Success<T>) {
        block(this.data)
    }
    return this
}

fun <T : Any> Result<T>.whenFail(block: (Exception) -> Unit): Result<T> {
    if (this is Result.Error) {
        block(this.exception)
    }
    return this
}

suspend fun <T : Any, R : Any> Result<T>.unwrapSuspendResult(
    success: suspend (data: T) -> R,
    error: (Exception) -> R
): R {
    return when (this) {
        is Result.Success -> {
            success(data)
        }
        is Result.Error ->
            error(exception)
    }
}

fun <T : Any, R : Any> Result<T>.unwrapResult(
    success: (data: T) -> R,
    error: (Exception) -> R
): R {
    return when (this) {
        is Result.Success ->
            success(data)
        is Result.Error ->
            error(exception)
    }
}

fun <T : Any, R : Any> Result<T>.handleResult(
    success: (data: T) -> R,
    error: (Exception) -> Exception
): Result<R> {
    return this.unwrapResult({
        Result.Success(
            success(
                it
            )
        )
    }, {
        Result.Error(error(it))
    })
}

suspend fun <R : Any> tryCall(block: suspend () -> Result<R>): Result<R> {
    return try {
        block()
    } catch (e: Exception) {
        Result.Error(e)
    }
}