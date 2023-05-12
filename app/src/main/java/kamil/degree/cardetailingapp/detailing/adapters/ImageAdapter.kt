package kamil.degree.cardetailingapp.detailing.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kamil.degree.cardetailingapp.R

class ImageAdapter(var urls: List<String>): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    interface OnImageClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick (position: Int)
    }

    fun setOnItemClickListener(listener: OnImageClickListener) {
        mListener = listener
    }


    private lateinit var mListener: OnImageClickListener
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

    inner class ImageViewHolder(itemView: View, listener: OnImageClickListener):
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