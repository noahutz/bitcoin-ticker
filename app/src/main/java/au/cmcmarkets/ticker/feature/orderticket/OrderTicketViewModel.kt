package au.cmcmarkets.ticker.feature.orderticket

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.cmcmarkets.ticker.data.PriceRepository
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
    }

    private var pollingJob: Job? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startPolling() {
        pollingJob = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                delay(DELAY_POLL)
                priceRepository.getPriceUpdates()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }
}
