package com.sample.vkoelassign.network

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.Response
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.sample.vkoelassign.utility.NoInternetException
import com.sample.vkoelassign.R

class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val applicationContext = context.applicationContext

    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {
            throw NoInternetException(applicationContext.getString(R.string.text_make_sure_no_data_connection))
        }


        return chain.proceed(chain.request())
    }

    /**
     * Method returns true is Internet is available else false
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }
}