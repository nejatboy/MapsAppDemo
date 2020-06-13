package com.nejatboy.demoapp.service

import com.nejatboy.demoapp.model.BaseModel
import retrofit2.Response
import retrofit2.http.GET


//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=41.009582,29.075049&radius=1500&keyword=cafe&key=AIzaSyCd0Ruh6iW2OeT8UoEyQ493ZifqHH9eHbE
//https://jsonplaceholder.typicode.com/comments
//41.009582
//29.075049

//@Path("latitue") latitue:String, @Path("longitute") longitute: String
interface MyAPI {

    @GET("maps/api/place/nearbysearch/json?location=41.009582,29.075049&radius=1500&keyword=cafe&key=AIzaSyCd0Ruh6iW2OeT8UoEyQ493ZifqHH9eHbE")
    suspend fun getData() : Response<BaseModel>

}


