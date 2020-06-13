package com.nejatboy.demoapp.service

import com.nejatboy.demoapp.model.Comment
import retrofit2.Response
import retrofit2.http.GET


//https://jsonplaceholder.typicode.com/comments
interface MyAPI {

    @GET("comments")
    suspend fun getData() : Response<List<Comment>>


}