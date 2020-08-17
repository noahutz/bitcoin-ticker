package au.cmcmarkets.ticker.data

import au.cmcmarkets.ticker.data.api.BitcoinApi
import au.cmcmarkets.ticker.data.api.BitcoinPrice
import au.cmcmarkets.ticker.data.local.BitcoinDao
import au.cmcmarkets.ticker.data.local.BitcoinEntity
import javax.inject.Inject

class PriceRepository @Inject constructor(
    private val repository: BitcoinApi,
    private val dao: BitcoinDao
) {
    suspend fun getPriceUpdates(): Map<String, BitcoinPrice> {
        val priceUpdates = repository.getPriceUpdates()
        saveToDb(priceUpdates)
        return loadFromDb()
    }

    private fun loadFromDb(): Map<String, BitcoinPrice> {
        return dao.loadAll().mapValues {
            BitcoinPrice(
                price15m = it.value.price15m,
                priceLast = it.value.priceLast,
                priceBuy = it.value.priceBuy,
                priceSell = it.value.priceSell,
                currencySymbol = it.value.currencySymbol
            )
        }
    }

    private fun saveToDb(priceUpdates: Map<String, BitcoinPrice>) {
        priceUpdates.forEach {
            dao.saveAll(
                it.key,
                BitcoinEntity(
                    price15m = it.value.price15m,
                    priceLast = it.value.priceLast,
                    priceBuy = it.value.priceBuy,
                    priceSell = it.value.priceSell,
                    currencySymbol = it.value.currencySymbol
                )
            )
        }
    }
}
