package de.exercicse.jrossbach.podcaster.core.domain

import de.exercicse.jrossbach.podcaster.R
import javax.inject.Inject

class ErrorMapper @Inject constructor(
    private val resourceProvider: AndroidResourceProvider
) {

    fun mapError(throwable: Throwable): String {
        return when (throwable) {
            is NotFoundException -> resourceProvider.getString(R.string.not_found_error)
            is ServiceUnavailableException -> resourceProvider.getString(R.string.internal_server_error)
            is NetworkException -> resourceProvider.getString(R.string.network_error)
            else -> resourceProvider.getString(R.string.unknown_error)
        }
    }
}