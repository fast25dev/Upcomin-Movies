package dev.fast25.upcomingmovie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import dev.fast25.upcomingmovie.R
import dev.fast25.upcomingmovie.databinding.ActivityDetailBinding
import dev.fast25.upcomingmovie.model.DetailResponce
import dev.fast25.upcomingmovie.model.ImageResponse
import dev.fast25.upcomingmovie.model.ImagesAdapter
import dev.fast25.upcomingmovie.remote.ApiClient
import dev.fast25.upcomingmovie.util.Extensions.formatBalance
import dev.fast25.upcomingmovie.util.Extensions.hide
import dev.fast25.upcomingmovie.util.Extensions.imageUrlBack
import dev.fast25.upcomingmovie.util.Extensions.isInternetAvailable
import dev.fast25.upcomingmovie.util.Extensions.show
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var loading: LottieAnimationView
    private lateinit var internet: LottieAnimationView
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        id = intent.getIntExtra("id", 1)
        loading = findViewById(R.id.loading)
        internet = findViewById(R.id.noInternet)

        if (isInternetAvailable()) loadMovieInfo()
    }

    private fun loadMovieInfo() {
        loading.show()
        ApiClient.apiService.getMovieById(id).enqueue(object : Callback<DetailResponce> {
            override fun onResponse(
                call: Call<DetailResponce>,
                response: Response<DetailResponce>
            ) {
                loading.hide()
                if (response.isSuccessful) {
                    setData(response.body()!!)
                }
            }

            override fun onFailure(call: Call<DetailResponce>, t: Throwable) {
                loading.hide()
            }
        })
    }

    private fun setData(detailResponce: DetailResponce) {
        binding.apply {
            Glide.with(ivMovie).load(detailResponce.backdropPath?.imageUrlBack())
                .placeholder(R.drawable.img_place_holder).into(ivMovie)
            tvName.text = detailResponce.title
            tvMoney.text = detailResponce.revenue.toString().formatBalance()
            tvVote.text = detailResponce.voteCount.toString()
            tvLang.text = detailResponce.originalLanguage
            tvOverview.text = detailResponce.overview
            loadImages()
        }
    }

    private fun loadImages() {
        ApiClient.apiService.getImagesById(id).enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {
                    binding.rvImages.adapter = ImagesAdapter(response.body()!!.backdrops!!)
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {

            }
        })
    }
}