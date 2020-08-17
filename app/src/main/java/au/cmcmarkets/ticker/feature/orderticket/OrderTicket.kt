package au.cmcmarkets.ticker.feature.orderticket

import au.cmcmarkets.ticker.feature.orderticket.enums.PriceDirection
import java.math.BigDecimal

data class OrderTicket(
    val convertFrom: String,
    val convertTo: String,
    val price15m: BigDecimal,
    val priceLast: BigDecimal,
    val priceBuy: BigDecimal,
    val priceSell: BigDecimal,
    val currencySymbol: String,
    private val previousPriceBuy: BigDecimal? = null,
    private val previousPriceSell: BigDecimal? = null
) {
    val priceSpread: BigDecimal = (priceBuy - priceSell).abs()
    val priceBuyDirection: PriceDirection = getPriceDirection(priceBuy, previousPriceBuy)
    val priceSellDirection: PriceDirection = getPriceDirection(priceSell, previousPriceSell)

    private fun getPriceDirection(newPrice: BigDecimal, oldPrice: BigDecimal?): PriceDirection {
        return when {
            oldPrice == null -> PriceDirection.NEUTRAL
            newPrice < oldPrice -> PriceDirection.DOWN
            newPrice > oldPrice -> PriceDirection.UP
            else -> PriceDirection.NEUTRAL
        }
    }
}
