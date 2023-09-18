package dev.fast25.upcomingmovie.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.fast25.upcomingmovie.R
import dev.fast25.upcomingmovie.util.Extensions.imageUrl

class ImagesAdapter(private val list: ArrayList<BackdropsItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_images, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            holder.apply {
                Glide.with(image).load(list[position].filePath?.imageUrl())
                    .placeholder(R.drawable.img_place_holder).into(image)
            }
        }
    }

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
    }
}