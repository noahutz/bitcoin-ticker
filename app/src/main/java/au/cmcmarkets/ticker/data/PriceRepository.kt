package au.cmcmarkets.ticker.data

import au.cmcmarkets.ticker.data.api.BitcoinApi
import au.cmcmarkets.ticker.data.api.BitcoinPrice
import javax.inject.Inject

class PriceRepository @Inject constructor(private val repository: BitcoinApi) {
    suspend fun getPriceUpdates(): Map<String, BitcoinPrice> = repository.getPriceUpdates()
}
