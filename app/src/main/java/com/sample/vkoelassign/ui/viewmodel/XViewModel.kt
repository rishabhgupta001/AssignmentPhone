package com.sample.vkoelassign.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sample.vkoelassign.network.Api
import com.sample.vkoelassign.network.StatusCode
import com.sample.vkoelassign.ui.model.MovieResponseModel
import com.sample.vkoelassign.utility.Constants
import com.sample.vkoelassign.utility.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class XViewModel(application: Application) : AndroidViewModel(application) {
    val movieResponse: MutableLiveData<MovieResponseModel> = MutableLiveData()
    //MovieResponseModel

    // get list of notification
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("CheckResult")
    fun getMovieList() {
        val responseModel = MovieResponseModel()
        if (!Utils.isInternetAvailable(getApplication())) {
            responseModel.statusCode = StatusCode.NETWORK_ERROR
            movieResponse.value = responseModel
            //dummy api key:- 079e456f84e8b0a30f1bf79918971cb3ccf3e3ab836ab64300e14d6f
        } else {
            val observable = Api.create()
                .getMovieList(Constants.API_KEY,Constants.LANGUAGE,Constants.PAGE)
            observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    responseModel.statusCode = StatusCode.START
                    movieResponse.postValue(responseModel)
                }
                .subscribe({ success ->
                    success.statusCode = StatusCode.SUCCESS
                    movieResponse.postValue(success)
                }, { it ->
                    responseModel.msg = it.localizedMessage
                    responseModel.statusCode = StatusCode.ERROR
                    movieResponse.postValue(responseModel)
                }, {})
        }
    }
}