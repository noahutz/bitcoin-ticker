package au.cmcmarkets.ticker.utils

import java.lang.NumberFormatException
import java.math.BigDecimal

fun String.hasValue(): Boolean {
    return when (val result = toDecimal()) {
        is DecimalConversion.Success -> result.value > BigDecimal.ZERO
        is DecimalConversion.Error -> false
    }
}

fun String.toDecimal(): DecimalConversion {
    return try {
        val value = BigDecimal(this)
        DecimalConversion.Success(value)
    } catch (e: NumberFormatException) {
        DecimalConversion.Error(e)
    }
}

sealed class DecimalConversion {
    data class Success(val value: BigDecimal) : DecimalConversion()
    data class Error(val exception: Exception) : DecimalConversion()
}