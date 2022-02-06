package com.lab.tinkoff.gifapp.api

import com.lab.tinkoff.gifapp.entity.api.ResponseApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface ApiService {


    @GET("{category}/{page}?json=true")
    suspend fun get_gifs(@Path("category") category: String,
                         @Path("page") page: Int): Response<ResponseApi>

}