package kamil.degree.cardetailingapp.detailing.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.model.Service


class BusinessServiceOverviewAdapter (var serviceList: MutableList<Service>)
    : RecyclerView.Adapter<BusinessServiceOverviewAdapter.BusinessServiceOverviewViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessServiceOverviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.service_overview, parent, false)

        return BusinessServiceOverviewViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: BusinessServiceOverviewViewHolder, position: Int) {
        val service = serviceList[position]
        holder.serviceName.text = service.name
        holder.servicePrice.text = if (service.price == null) "" else service.price.toString()
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    inner class BusinessServiceOverviewViewHolder(view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val serviceName: TextView = itemView.findViewById(R.id.serviceName)
        val servicePrice: TextView = itemView.findViewById(R.id.servicePrice)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

}



