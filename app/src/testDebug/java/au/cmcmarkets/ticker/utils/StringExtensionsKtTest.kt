package au.cmcmarkets.ticker.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class StringExtensionsKtTest {
    @Test
    fun `test String non null BigDecimal`() {
        assertNotNull("123.45".toBigDecimalValueOrNull())
        assertNotNull("121213313.4121212125".toBigDecimalValueOrNull())
        assertNotNull("12123423.443245".toBigDecimalValueOrNull())
        assertNotNull("1324123".toBigDecimalValueOrNull())
        assertNotNull("1.21212".toBigDecimalValueOrNull())
        assertNotNull("1.21212".toBigDecimalValueOrNull())
        assertNotNull("121,213,313.4121212125".toBigDecimalValueOrNull())
    }

    @Test
    fun `test String null BigDecimal`() {
        assertNull("0".toBigDecimalValueOrNull())
        assertNull(" ".toBigDecimalValueOrNull())
        assertNull("a".toBigDecimalValueOrNull())
        assertNull("     ".toBigDecimalValueOrNull())
    }

    @Test
    fun `test String has BigDecimal value`() {
        assertTrue("123.45".hasBigDecimalValue())
        assertTrue("121213313.4121212125".hasBigDecimalValue())
        assertTrue("12123423.443245".hasBigDecimalValue())
        assertTrue("1324123".hasBigDecimalValue())
        assertTrue("1.21212".hasBigDecimalValue())
        assertTrue("1.21212".hasBigDecimalValue())
        assertTrue("121,213,313.4121212125".hasBigDecimalValue())
    }

    @Test
    fun `test String no BigDecimal value`() {
        assertFalse("0".hasBigDecimalValue())
        assertFalse(" ".hasBigDecimalValue())
        assertFalse(".".hasBigDecimalValue())
        assertFalse("     ".hasBigDecimalValue())
    }
}