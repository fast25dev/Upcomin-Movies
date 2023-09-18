package dev.fast25.upcomingmovie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.fast25.upcomingmovie.R
import dev.fast25.upcomingmovie.model.ResultsItem
import dev.fast25.upcomingmovie.util.Extensions.imageUrl

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<ResultsItem>()
    var itemClick: ((Int) -> Unit)? = null

    fun submitList(list: ArrayList<ResultsItem>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = list[position]
        if (holder is MovieViewHolder) {
            holder.apply {
                item.setOnClickListener {
                    itemClick?.invoke(movie.id!!)
                }

                Glide.with(ivMovie)
                    .load(movie.posterPath?.imageUrl())
                    .placeholder(R.drawable.img_place_holder)
                    .into(ivMovie)

                tvTitle.text = movie.title ?: "No title"
                tvVote.text = "${((movie.voteAverage ?: 1.0)* 10).toInt()}/100"
                tvPopularity.text = "${movie.popularity?.toInt()}"
                tvReleaseDate.text = movie.releaseDate
                tvLang.text = movie.originalLanguage
                tvOverview.text = movie.overview
            }
        }
    }

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item: CardView = view.findViewById(R.id.item)
        val ivMovie: ImageView = view.findViewById(R.id.ivMovie)
        val tvTitle: TextView = view.findViewById(R.id.tvName)
        val tvLang: TextView = view.findViewById(R.id.tvLang)
        val tvVote: TextView = view.findViewById(R.id.tvVote)
        val tvReleaseDate: TextView = view.findViewById(R.id.tvReleaseDate)
        val tvPopularity: TextView = view.findViewById(R.id.tvPopularity)
        val tvOverview: TextView = view.findViewById(R.id.tvOverview)
    }
}