package au.cmcmarkets.ticker.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class BigDecimalExtensionsKtTest {

    @Test
    fun `test big decimal to formatted currency`() {
        assertEquals(BigDecimal("345.678").toFormattedCurrency(), "345.68")
        assertEquals(BigDecimal("2345.6789").toFormattedCurrency(), "2,345.68")
        assertEquals(BigDecimal("12345.6789").toFormattedCurrency(), "12,345.68")
    }
}
