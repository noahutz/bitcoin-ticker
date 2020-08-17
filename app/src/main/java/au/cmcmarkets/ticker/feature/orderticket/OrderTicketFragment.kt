package au.cmcmarkets.ticker.feature.orderticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import au.cmcmarkets.ticker.R
import au.cmcmarkets.ticker.core.di.viewmodel.ViewModelFactory
import au.cmcmarkets.ticker.utils.toFormattedString
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_order_ticket.*
import javax.inject.Inject

class OrderTicketFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: OrderTicketViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(OrderTicketViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_order_ticket, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.orderTicket.observe(viewLifecycleOwner, Observer {
            updateOrderTicketView(it)
        })
    }

    private fun updateOrderTicketView(orderTicket: OrderTicket) {
        tvTitle.text = String.format("%s - %s", orderTicket.convertFrom, orderTicket.convertTo)
        tvAmount.text = getString(R.string.value_amount, orderTicket.currencySymbol)
        tvPriceBuy.text = orderTicket.priceBuy.toFormattedString()
        tvPriceSell.text = orderTicket.priceSell.toFormattedString()
        tvSpread.text = orderTicket.priceSpread.toFormattedString()
        tvPriceDelayed.text = orderTicket.price15m.toFormattedString()
        tvPriceLast.text = orderTicket.priceLast.toFormattedString()

        tvPriceBuy.setTextColor(getTextColor(orderTicket.priceBuyDirection))
        tvPriceSell.setTextColor(getTextColor(orderTicket.priceSellDirection))
    }

    private fun getTextColor(priceDirection: PriceDirection): Int {
        val colorId = when (priceDirection) {
            PriceDirection.NEUTRAL -> R.color.text_color_neutral
            PriceDirection.UP -> R.color.text_color_up
            PriceDirection.DOWN -> R.color.text_color_down
        }
        return ResourcesCompat.getColor(resources, colorId, null)
    }
}
