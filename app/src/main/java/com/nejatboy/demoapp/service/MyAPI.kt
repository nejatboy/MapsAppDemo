package com.nejatboy.demoapp.service

import com.nejatboy.demoapp.model.BaseModel
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=41.009582,29.075049&radius=1500&keyword=cafe&key=AIzaSyCd0Ruh6iW2OeT8UoEyQ493ZifqHH9eHbE

interface MyAPI {

    @GET("maps/api/place/nearbysearch/json")
    suspend fun getData(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("keyword") keyword: String,
        @Query("key") key: String
    ) : Response<BaseModel>

}


