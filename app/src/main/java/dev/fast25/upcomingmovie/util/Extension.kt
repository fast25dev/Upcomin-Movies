package dev.fast25.upcomingmovie.util

import android.app.Activity
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.view.View
import androidx.fragment.app.Fragment

object Extensions {

    fun String.imageUrl() = "https://image.tmdb.org/t/p/w500$this"
    fun String.imageUrlBack() = "https://image.tmdb.org/t/p/original$this"

    fun String.formatBalance(): String {
        var s = this
        s = s.substring(0, s.length - 2)
        var result = s[s.length - 1].toString()
        for (i in 1 until s.length) {
            result = if (i % 3 == 0) {
                "${s[s.length - i - 1]} $result"
            } else {
                s[s.length - i - 1] + result
            }
        }
        return result

    }

    fun youtubeImage(id: String) = "https://img.youtube.com/vi/${id}/0.jpg"


    fun youtubeOpen(path: String) = "https://www.youtube.com/watch?v=${path}"

    fun View.hide() {
        this.visibility = View.GONE
    }

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun Fragment.isInternetAvailable(): Boolean {
        val manager = requireContext().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }

    fun Activity.isInternetAvailable(): Boolean {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val infoMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        val infoWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return infoMobile!!.isConnected || infoWifi!!.isConnected
    }
}