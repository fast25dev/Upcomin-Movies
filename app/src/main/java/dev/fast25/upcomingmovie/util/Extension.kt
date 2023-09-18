package dev.fast25.upcomingmovie.util

import android.app.Activity
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.view.View
import androidx.fragment.app.Fragment

object Extensions {

    fun String.imageUrl()= "https://image.tmdb.org/t/p/original$this"
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