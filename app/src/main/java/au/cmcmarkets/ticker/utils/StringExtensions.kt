package au.cmcmarkets.ticker.utils

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.ParseException

fun String.toBigDecimalValueOrNull(): BigDecimal? {
    return try {
        val number = DecimalFormat.getNumberInstance().parse(this)
        number?.let { BigDecimal(it.toString()) }
    } catch (e: ParseException) {
        null
    }
}

fun String.hasBigDecimalValue(): Boolean {
    return toBigDecimalValueOrNull()?.let { it > BigDecimal.ZERO } ?: false
}
