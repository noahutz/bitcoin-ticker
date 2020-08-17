package au.cmcmarkets.ticker.feature.orderticket

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.cmcmarkets.ticker.data.PriceRepository
import au.cmcmarkets.ticker.data.api.BitcoinPrice
import au.cmcmarkets.ticker.feature.orderticket.enums.EditValueChanged
import au.cmcmarkets.ticker.utils.PriceComputations
import au.cmcmarkets.ticker.utils.toBigDecimalValueOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject


class OrderTicketViewModel @Inject constructor(
    private val priceRepository: PriceRepository
) : ViewModel(), LifecycleObserver {
    companion object {
        private const val DELAY_POLL = 100L

        // TODO(Improvement): Should be set when fragment
        private const val BITCOIN_CODE = "BTC"

        // TODO(Improvement): Should be set when fragment has been loaded with specific currency
        private const val CURRENCY_CODE = "GBP"
    }

    fun getOrderTicket(): MutableLiveData<OrderTicket> = orderTicket
    fun getUnitLiveData(): MutableLiveData<BigDecimal> = unitLiveData
    fun getAmountLiveData(): MutableLiveData<BigDecimal> = amountLiveData

    private val orderTicket: MutableLiveData<OrderTicket> = MutableLiveData()
    private val unitLiveData: MutableLiveData<BigDecimal> = MutableLiveData()
    private val amountLiveData: MutableLiveData<BigDecimal> = MutableLiveData()

    private var lastEditValueChanged: EditValueChanged = EditValueChanged.NONE
    private var valueUnit: BigDecimal = BigDecimal.ZERO
    private var valueAmount: BigDecimal = BigDecimal.ZERO

    private var pollingJob: Job? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startPolling() {
        pollingJob = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                val priceUpdates = priceRepository.getPriceUpdates()
                getOrderTicket(priceUpdates, orderTicket.value)?.let {
                    orderTicket.postValue(it)
                }
                updateEditValues()
                delay(DELAY_POLL)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }

    fun setUnit(unitString: String) {
        valueUnit = unitString.toBigDecimalValueOrNull() ?: BigDecimal.ZERO
        valueAmount = PriceComputations.getAmount(valueUnit, orderTicket.value?.priceBuy)
        amountLiveData.postValue(valueAmount)
        lastEditValueChanged = EditValueChanged.UNIT
    }

    fun setAmount(amountString: String) {
        valueAmount = amountString.toBigDecimalValueOrNull() ?: BigDecimal.ZERO
        valueUnit = PriceComputations.getUnit(valueAmount, orderTicket.value?.priceBuy)
        unitLiveData.postValue(valueUnit)
        lastEditValueChanged = EditValueChanged.AMOUNT
    }

    private fun updateEditValues() {
        when (lastEditValueChanged) {
            EditValueChanged.AMOUNT -> setAmount(valueAmount.toString())
            EditValueChanged.UNIT -> setUnit(valueUnit.toString())
            else -> {
            }
        }
    }

    private fun getOrderTicket(
        priceUpdates: Map<String, BitcoinPrice>,
        oldOrderTicket: OrderTicket?
    ): OrderTicket? {
        return priceUpdates[CURRENCY_CODE]?.toOrderTicket()?.copy(
            previousPriceSell = oldOrderTicket?.priceSell,
            previousPriceBuy = oldOrderTicket?.priceBuy
        )
    }

    private fun BitcoinPrice.toOrderTicket(): OrderTicket {
        return OrderTicket(
            BITCOIN_CODE,
            CURRENCY_CODE,
            price15m = price15m,
            priceLast = priceLast,
            priceBuy = priceBuy,
            priceSell = priceSell,
            currencySymbol = currencySymbol
        )
    }
}

