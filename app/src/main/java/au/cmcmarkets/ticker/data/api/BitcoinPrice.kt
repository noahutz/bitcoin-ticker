package au.cmcmarkets.ticker.data.api

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class BitcoinPrice(
    @SerializedName("15m") val price15m: BigDecimal,
    @SerializedName("last") val priceLast: BigDecimal,
    @SerializedName("buy") val priceBuy: BigDecimal,
    @SerializedName("sell") val priceSell: BigDecimal,
    @SerializedName("symbol") val currencySymbol: String
)
