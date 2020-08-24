package de.exercicse.jrossbach.podcaster.core.domain

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateParser @Inject constructor() {

    fun getFormattedDate(dateTime: String): String? {
        return parseDate(dateTime)?.let {
            SimpleDateFormat(WEATHER_DATE_OUTPUT_FORMAT, Locale.ROOT)
                .format(it)
        }
    }

    private fun parseDate(dateTime: String): Date? {
        return try {
            SimpleDateFormat(WEATHER_DATE_INPUT_FORMAT, Locale.ROOT).parse(dateTime)
        } catch (e: ParseException) {
            null
        }
    }

    companion object {
        private const val WEATHER_DATE_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss"
        private const val WEATHER_DATE_OUTPUT_FORMAT = "EE, dd. MMMM HH:mm"
    }
}