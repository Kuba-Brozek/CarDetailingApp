package kamil.degree.cardetailingapp.detailing.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kamil.degree.cardetailingapp.R
import kamil.degree.cardetailingapp.model.Business

class BusinessSearchAdapter(var businessList: List<Pair<Business, String>>): RecyclerView.Adapter<BusinessSearchAdapter.BusinessSearchViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): BusinessSearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.business_search_list_item, parent, false)
        return BusinessSearchViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: BusinessSearchViewHolder, position: Int) {
        val business = businessList[position]

        holder.businessName.text = business.first.name
        holder.businessDesc.text = business.first.description
        if (business.second != "") {
            Glide.with(holder.itemView).load(business.second).into(holder.businessImg)
        }
    }


    inner class BusinessSearchViewHolder(view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val businessName: TextView = itemView.findViewById(R.id.business_search_name_TV)
        val businessDesc: TextView = itemView.findViewById(R.id.business_search_desc_TV)
        val businessImg: ImageView = itemView.findViewById(R.id.business_search_img_IV)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }

        }
    }

    override fun getItemCount(): Int = businessList.size


}