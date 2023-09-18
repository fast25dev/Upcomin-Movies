package dev.fast25.upcomingmovie.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import dev.fast25.upcomingmovie.R
import dev.fast25.upcomingmovie.adapter.MovieAdapter
import dev.fast25.upcomingmovie.databinding.ActivityMainBinding
import dev.fast25.upcomingmovie.model.Movie
import dev.fast25.upcomingmovie.model.ResultsItem
import dev.fast25.upcomingmovie.remote.ApiClient
import dev.fast25.upcomingmovie.util.Extensions.hide
import dev.fast25.upcomingmovie.util.Extensions.isInternetAvailable
import dev.fast25.upcomingmovie.util.Extensions.show
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { MovieAdapter() }
    private val list by lazy { ArrayList<ResultsItem>() }
    private lateinit var loading: LottieAnimationView
    private lateinit var internet: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        loading = findViewById(R.id.loading)
        internet = findViewById(R.id.noInternet)

        if (isInternetAvailable()) {
            internet.hide()
            loadMovies()
        } else internet.show()

        binding.apply {
            rvUpcoming.adapter = adapter
        }
    }

    private fun loadMovies() {
        loading.show()
        ApiClient.apiService.getUpComingMovie().enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                loading.hide()
                if (response.isSuccessful) {
                    response.body()!!.results?.let {
                        list.addAll(it)
                        adapter.submitList(it)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "${response.errorBody()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                loading.hide()
                Log.d("@@@@@@", "onFailure: ${t.localizedMessage}")
                Toast.makeText(this@MainActivity, "${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

        })
    }
}