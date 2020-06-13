package com.nejatboy.demoapp.service

import com.nejatboy.demoapp.model.BaseModel
import com.nejatboy.demoapp.model.Comment
import retrofit2.Response
import retrofit2.http.GET

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=41.009582,29.075049&rankby=distance&type=food&key=AIzaSyCfZhVAc4qtJg6y8DqCcDYOdIHRsR1tbz0
//https://jsonplaceholder.typicode.com/comments
interface MyAPI {

    @GET("maps/api/place/nearbysearch/json?location=41.009582,29.075049&rankby=distance&type=food&key=AIzaSyCfZhVAc4qtJg6y8DqCcDYOdIHRsR1tbz0")
    suspend fun getData() : Response<BaseModel>


}