package kamil.degree.cardetailingapp.detailing.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.model.Service

class ServicesOverviewAdapter (private val serviceList: List<Service>) : RecyclerView.Adapter<ServicesOverviewAdapter.ServicesOverviewViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesOverviewViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.service_overview_list_item, parent, false)

            return ServicesOverviewViewHolder(view)
        }

        override fun onBindViewHolder(holder: ServicesOverviewViewHolder, position: Int) {
            val service = serviceList[position]

            holder.serviceName.setText(service.name)
            holder.servicePrice.setText(service.price!!.toString())

        }

        override fun getItemCount(): Int {
            return serviceList.size
        }

        class ServicesOverviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val serviceName: EditText = itemView.findViewById(R.id.serviceName)
            val servicePrice: EditText = itemView.findViewById(R.id.servicePrice)
        }
    }
