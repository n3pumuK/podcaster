package de.exercicse.jrossbach.podcaster.core.domain

import io.reactivex.Single
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class DomainExceptionMapper @Inject constructor() {

    fun <T> mapException(throwable: Throwable): Single<T> {
        return when {
            throwable is HttpException -> mapHttpError(throwable)
            isNetworkException(throwable) -> NetworkException()
            else -> throwable
        }.let { Single.error(it) }
    }

    private fun mapHttpError(httpError: HttpException): Exception {
        return when (httpError.code()) {
            HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException()
            HttpURLConnection.HTTP_INTERNAL_ERROR, HttpURLConnection.HTTP_UNAVAILABLE -> ServiceUnavailableException()
            else -> httpError
        }
    }

    private fun isNetworkException(throwable: Throwable): Boolean {
        return when (throwable) {
            is UnknownHostException,
            is SocketTimeoutException,
            is InterruptedIOException,
            is SocketException -> true
            else -> false
        }
    }
}