package com.sample.vkoelassign.network

import com.sample.vkoelassign.ui.model.MovieResponseModel
import com.sample.vkoelassign.utility.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

interface Api {


    companion object {
        fun create(): Api {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()

            return retrofit.create(Api::class.java)
        }
    }


    // // //https://api.themoviedb.org/3/movie/popular?api_key=38a73d59546aa378980a88b645f487fc&language=en-US&page=1
    @GET("3/movie/popular")
    //query needed if there is any query
    fun getMovieList(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: String,
    ): Observable<MovieResponseModel>


}