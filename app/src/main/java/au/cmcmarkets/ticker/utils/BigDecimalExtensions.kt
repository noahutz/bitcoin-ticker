package au.cmcmarkets.ticker.utils

import java.math.BigDecimal
import java.math.RoundingMode

private const val DECIMAL_POINTS = 2
private val ROUNDING_MODE = RoundingMode.HALF_EVEN

fun BigDecimal.toFormattedString(): String {
    return this.setScale(DECIMAL_POINTS, ROUNDING_MODE).toString()
}
