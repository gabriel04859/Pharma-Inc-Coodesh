package com.gabrielribeiro.pharma_inc_coodesh.utils

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object BackendUtils {

    private val locale: Locale = Locale.forLanguageTag("pt-BR")
    private val df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale)
    private val birthDayDF: DateFormat = SimpleDateFormat("dd/MM/yyyy", locale)

    fun parseDateTime(dateString: String): Date? {
        return df.parse(dateString)
    }

    fun formatBirthDay(date: Date): String {
        Log.d("dddd", "formatBirthDay: ${birthDayDF.format(date)}")
        return birthDayDF.format(date)
    }

    fun initialsCountry(country : String): String {
        return when(country) {
            "Autralia" -> "AU"
            "Brasil" -> "BR"
            "Canadá" -> "CA"
            "Suíça" -> "CH"
            "Alemanha" -> "DE"
            "Dinamarca" -> "DK"
            "Espanha" -> "ES"
            "Finlândia" -> "FI"
            else -> ""
        }
    }
}