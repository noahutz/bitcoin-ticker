package au.cmcmarkets.ticker.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

fun BigDecimal.toFormattedCurrency(): String {
    val decimalFormat = DecimalFormat.getInstance()
    return decimalFormat.format(setScale(2, RoundingMode.HALF_EVEN))
}

fun BigDecimal.toFormattedString(): String {
    return this.stripTrailingZeros().toString()
}
