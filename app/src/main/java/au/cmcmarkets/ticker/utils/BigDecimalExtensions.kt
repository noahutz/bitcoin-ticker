package au.cmcmarkets.ticker.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Formats BigDecimal to round of to 2 decimal places
 */
fun BigDecimal.toFormattedString(): String {
    return DecimalFormat.getInstance().format(setScale(2, RoundingMode.HALF_EVEN))
}
