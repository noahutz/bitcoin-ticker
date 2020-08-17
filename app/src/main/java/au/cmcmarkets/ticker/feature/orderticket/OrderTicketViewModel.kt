package au.cmcmarkets.ticker.feature.orderticket

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.cmcmarkets.ticker.data.PriceRepository
import au.cmcmarkets.ticker.data.api.BitcoinPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class OrderTicketViewModel @Inject constructor(
    private val priceRepository: PriceRepository
) : ViewModel(), LifecycleObserver {
    companion object {
        private const val DELAY_POLL = 500L

        // TODO(Set from fragment)
        private const val CURRENCY_CODE = "GBP"
    }

    var orderTicket: MutableLiveData<OrderTicket> = MutableLiveData()
    private var pollingJob: Job? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startPolling() {
        pollingJob = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(DELAY_POLL)
                val priceUpdates = priceRepository.getPriceUpdates()
                priceUpdates[CURRENCY_CODE]?.let {
                    orderTicket.postValue(it.toBitcoinPrice())
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }
}

private fun BitcoinPrice.toBitcoinPrice(): OrderTicket =
    OrderTicket(
        price15m = price15m,
        priceLast = priceLast,
        priceBuy = priceBuy,
        priceSell = priceSell,
        currencySymbol = currencySymbol
    )

