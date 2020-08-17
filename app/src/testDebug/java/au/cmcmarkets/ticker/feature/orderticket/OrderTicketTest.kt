package au.cmcmarkets.ticker.feature.orderticket

import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class OrderTicketTest {

    private val orderTicket = OrderTicket(
        convertFrom = "BTC",
        convertTo = "GBP",
        price15m = BigDecimal(10),
        priceLast = BigDecimal(10),
        priceBuy = BigDecimal(10),
        priceSell = BigDecimal(10),
        currencySymbol = "£",
        previousPriceBuy = null,
        previousPriceSell = null
    )

    @Test
    fun `order ticket test without old price values returns price direction as neutral`() {
        assertEquals(orderTicket.priceBuyDirection, PriceDirection.NEUTRAL)
        assertEquals(orderTicket.priceSellDirection, PriceDirection.NEUTRAL)
    }

    @Test
    fun `order ticket test without old higher price values returns price direction as up`() {
        val orderTicketUp =
            orderTicket.copy(previousPriceBuy = BigDecimal(8), previousPriceSell = BigDecimal(8))
        assertEquals(orderTicketUp.priceBuyDirection, PriceDirection.UP)
        assertEquals(orderTicketUp.priceSellDirection, PriceDirection.UP)
    }

    @Test
    fun `order ticket test without old higher price values returns price direction as down`() {
        val orderTicketDown =
            orderTicket.copy(previousPriceBuy = BigDecimal(12), previousPriceSell = BigDecimal(12))
        assertEquals(orderTicketDown.priceBuyDirection, PriceDirection.DOWN)
        assertEquals(orderTicketDown.priceSellDirection, PriceDirection.DOWN)
    }
}