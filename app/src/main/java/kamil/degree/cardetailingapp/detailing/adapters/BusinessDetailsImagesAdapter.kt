package kamil.degree.cardetailingapp.detailing.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kamil.degree.cardetailingapp.R

class BusinessDetailsImagesAdapter(var urls: List<String>): RecyclerView.Adapter<BusinessDetailsImagesAdapter.ImageViewHolder>() {

    interface OnImage2ClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick (position: Int)
    }

    fun setOnItemClickListener(listener: OnImage2ClickListener) {
        mListener = listener
    }


    private lateinit var mListener: OnImage2ClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view, mListener)
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = urls[position]
        Glide.with(holder.itemView).load(url).into(holder.picture)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    inner class ImageViewHolder(itemView: View, listener: OnImage2ClickListener):
        RecyclerView.ViewHolder(itemView){
        val picture: ImageView = itemView.findViewById(R.id.ivImage)

        init {
            picture.setOnClickListener {
                listener.onItemClick(absoluteAdapterPosition)
            }
            picture.setOnLongClickListener {
                listener.onItemLongClick(absoluteAdapterPosition)
                true
            }
        }
    }


}