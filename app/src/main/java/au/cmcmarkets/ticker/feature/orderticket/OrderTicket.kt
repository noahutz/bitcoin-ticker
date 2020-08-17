package au.cmcmarkets.ticker.feature.orderticket

import java.math.BigDecimal

data class OrderTicket(
    val convertFrom: String,
    val convertTo: String,
    val price15m: BigDecimal,
    val priceLast: BigDecimal,
    val priceBuy: BigDecimal,
    val priceSell: BigDecimal,
    val currencySymbol: String
)
