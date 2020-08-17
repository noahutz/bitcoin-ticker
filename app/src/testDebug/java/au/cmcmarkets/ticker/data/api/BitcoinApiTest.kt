package au.cmcmarkets.ticker.data.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class BitcoinApiTest {
    private var mockWebServer = MockWebServer()
    private lateinit var bitcoinApi: BitcoinApi

    @Before
    fun setup() {
        mockWebServer.start()
        bitcoinApi = Retrofit.Builder()
            .baseUrl("https://blockchain.info//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BitcoinApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getPriceUpdates should should contain GBP price update`() = runBlocking {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(response)

        val result = bitcoinApi.getPriceUpdates()

        assertNotNull(result)
        assertTrue(result.containsKey("GBP"))
    }
}