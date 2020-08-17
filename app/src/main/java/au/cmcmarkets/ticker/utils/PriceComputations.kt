package au.cmcmarkets.ticker.utils

import java.math.BigDecimal

object PriceComputations {
    fun getAmount(unit: BigDecimal?, priceBuy: BigDecimal?): BigDecimal {
        return if (unit == null || priceBuy == null) BigDecimal.ZERO
        else unit * priceBuy
    }

    fun getUnit(amount: BigDecimal?, priceBuy: BigDecimal?): BigDecimal {
        return if (amount == null || priceBuy == null) BigDecimal.ZERO
        else amount / priceBuy
    }
}