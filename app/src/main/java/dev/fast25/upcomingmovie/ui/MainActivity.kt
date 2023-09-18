package dev.fast25.upcomingmovie.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import dev.fast25.upcomingmovie.R
import dev.fast25.upcomingmovie.adapter.MovieAdapter
import dev.fast25.upcomingmovie.databinding.ActivityMainBinding
import dev.fast25.upcomingmovie.model.Movie
import dev.fast25.upcomingmovie.model.ResultsItem
import dev.fast25.upcomingmovie.remote.ApiClient
import dev.fast25.upcomingmovie.util.EndlessRecyclerViewScrollListener
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
    private var page = 0
    private var isSearch = false
    private var totalPage = 1
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
        val manager = LinearLayoutManager(this)
        binding.apply {
            rvUpcoming.adapter = adapter
            rvUpcoming.layoutManager = manager
            val scrollListener = object : EndlessRecyclerViewScrollListener(manager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    if (!isSearch) loadMovies()
                }
            }
            rvUpcoming.addOnScrollListener(scrollListener)
            etSearch.addTextChangedListener {

                if (it.toString().length >= 2) {
                    isSearch = true
                    searchMovie(it.toString())
                } else {
                    adapter.submitList(ArrayList(), true)
                }
                if (it.toString().isEmpty()) {
                    isSearch = false
                    loadMovies()
                }
            }

        }
        adapter.itemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }
    }

    private fun loadMovies() {
        loading.show()
        if (page <= totalPage) {
            page++
            ApiClient.apiService.getUpComingMovie(page).enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    loading.hide()
                    if (response.isSuccessful) {
                        totalPage = response.body()!!.totalPages!!
                        response.body()!!.results?.let {
                            adapter.submitList(it.shuffled() as ArrayList<ResultsItem>)
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Server is unavailable",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    loading.hide()
                    Toast.makeText(this@MainActivity, "UnKnown Error", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    private fun searchMovie(name: String) {
        loading.show()
        ApiClient.apiService.searchMovieByName(name).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                loading.hide()
                if (response.isSuccessful) {
                    response.body()!!.results?.let {
                        list.clear()
                        list.addAll(it)
                        adapter.submitList(list, isSearch)
                    }
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {

            }
        })
    }


}