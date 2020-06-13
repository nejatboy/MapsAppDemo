package com.nejatboy.demoapp.service

import com.nejatboy.demoapp.model.Comment
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAPIService {

    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MyAPI::class.java)


    suspend fun getDataFromAPI() : Response<List<Comment>> {
        return api.getData()
    }
}