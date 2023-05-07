package kamil.degree.cardetailingapp.detailing.rvadapters

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.model.Service


class ServicesOverviewAdapter (var serviceList: MutableList<Service>) : RecyclerView.Adapter<ServicesOverviewAdapter.ServicesOverviewViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onSettingsClick(position: Int)
        fun onDeleteClick(position: Int)
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesOverviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.service_overview_list_item, parent, false)

        return ServicesOverviewViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ServicesOverviewViewHolder, position: Int) {
        val service = serviceList[position]
        holder.servicePrice.inputType = InputType.TYPE_CLASS_NUMBER
        holder.serviceName.setText(service.name)
        holder.servicePrice.setText( if (service.price == null) "" else service.price.toString()  )
        holder.serviceNameTextChangeListener.updatePosition(position)
        holder.servicePriceTextChangeListener.updatePosition(position)

    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    inner class ServicesOverviewViewHolder(view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val serviceName: EditText = itemView.findViewById(R.id.serviceName)
        val servicePrice: EditText = itemView.findViewById(R.id.servicePrice)
        private val deleteServiceBtn: ImageButton = itemView.findViewById(R.id.delete_service_BTN)
        private val settingsServiceBtn: ImageButton =
            itemView.findViewById(R.id.settings_service_BTN)
        val serviceNameTextChangeListener = ServiceNameTextChangeListener()
        val servicePriceTextChangeListener = ServicePriceTextChangeListener()

        init {
            serviceName.addTextChangedListener(serviceNameTextChangeListener)
            servicePrice.addTextChangedListener(servicePriceTextChangeListener)
            itemView.setOnClickListener { listener.onItemClick(absoluteAdapterPosition) }
            settingsServiceBtn.setOnClickListener { listener.onSettingsClick(absoluteAdapterPosition) }
            deleteServiceBtn.setOnClickListener { listener.onDeleteClick(absoluteAdapterPosition) }
        }
    }

    inner class ServiceNameTextChangeListener : TextWatcher {
        private var mPosition: Int = 0
        fun updatePosition(position: Int) { mPosition = position }
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun afterTextChanged(editable: Editable) {
            if(editable.isNotEmpty()){
                serviceList[mPosition].name = editable.toString()
            }
        }
    }

    inner class ServicePriceTextChangeListener : TextWatcher {
        private var mPosition: Int = 0
        fun updatePosition(position: Int) { mPosition = position }
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun afterTextChanged(editable: Editable) {
            if(editable.isNotEmpty()) {
                serviceList[mPosition].price = editable.toString().toInt()
            }
        }
    }


}



