package au.cmcmarkets.ticker.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class BigDecimalExtensionsKtTest {

    @Test
    fun `test big decimal to formatted string`() {
        assertEquals(BigDecimal("345.6").toFormattedString(), "345.6")
        assertEquals(BigDecimal("345.678").toFormattedString(), "345.68")
        assertEquals(BigDecimal("2345.6789").toFormattedString(), "2,345.68")
        assertEquals(BigDecimal("12345.6789").toFormattedString(), "12,345.68")
        assertEquals(BigDecimal("0").toFormattedString(), "0")
        assertEquals(BigDecimal("0.00").toFormattedString(), "0")
    }
}
