package com.nejatboy.demoapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.nejatboy.demoapp.model.Comment
import com.nejatboy.demoapp.service.MyAPIService
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : BaseViewModel(application) {

    val comments = MutableLiveData<List<Comment>>()


    fun runData() {
        getDataFromAPI()
    }


    private fun getDataFromAPI () {
        launch {
            val response = MyAPIService().getDataFromAPI()
            if (response.isSuccessful) {
                response.body()?.let {
                    comments.value = it
                }
            }
        }
    }
}