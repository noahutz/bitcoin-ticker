package au.cmcmarkets.ticker.data.local

import java.math.BigDecimal

class BitcoinEntity(
    val price15m: BigDecimal,
    val priceLast: BigDecimal,
    val priceBuy: BigDecimal,
    val priceSell: BigDecimal,
    val currencySymbol: String
)
