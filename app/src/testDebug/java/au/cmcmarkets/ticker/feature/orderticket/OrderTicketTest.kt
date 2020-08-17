package au.cmcmarkets.ticker.feature.orderticket

import au.cmcmarkets.ticker.feature.orderticket.enums.PriceDirection
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class OrderTicketTest {

    private val orderTicket = OrderTicket(
        convertFrom = "BTC",
        convertTo = "GBP",
        price15m = BigDecimal(10),
        priceLast = BigDecimal(10),
        priceBuy = BigDecimal(10),
        priceSell = BigDecimal(20),
        currencySymbol = "£",
        previousPriceBuy = null,
        previousPriceSell = null
    )

    @Test
    fun `order ticket test with same price values returns price direction as neutral`() {
        assertEquals(orderTicket.priceBuyDirection, PriceDirection.NEUTRAL)
        assertEquals(orderTicket.priceSellDirection, PriceDirection.NEUTRAL)

        val orderTicketSame = orderTicket.copy(
            previousPriceBuy = orderTicket.priceBuy,
            previousPriceSell = orderTicket.priceSell
        )
        assertEquals(orderTicketSame.priceSellDirection, PriceDirection.NEUTRAL)
    }

    @Test
    fun `order ticket test with higher price values returns price direction as up`() {
        val orderTicketUp =
            orderTicket.copy(previousPriceBuy = BigDecimal(8), previousPriceSell = BigDecimal(18))
        assertEquals(orderTicketUp.priceBuyDirection, PriceDirection.UP)
        assertEquals(orderTicketUp.priceSellDirection, PriceDirection.UP)
    }

    @Test
    fun `order ticket test with lower price values returns price direction as down`() {
        val orderTicketDown =
            orderTicket.copy(previousPriceBuy = BigDecimal(12), previousPriceSell = BigDecimal(22))
        assertEquals(orderTicketDown.priceBuyDirection, PriceDirection.DOWN)
        assertEquals(orderTicketDown.priceSellDirection, PriceDirection.DOWN)
    }

    @Test
    fun `order ticket test price spread`() {
        assertEquals(orderTicket.priceSpread, BigDecimal.TEN)
    }
}