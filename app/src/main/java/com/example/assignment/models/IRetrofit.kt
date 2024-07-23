package com.example.assignment.models

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IRetrofit {
    @POST("users/register")
    fun register(@Body body:RegisterRequestModel): Call<RegisterResponseModel>

    @POST("users/login")
    fun login(@Body body: LoginRequestModel): Call<LoginResponseModel>

    @GET("products?")
    fun getProduct(@Query("category") limit:String?): Call<ProductResponseModel>

    @GET("products/{id}")
    fun getProductById(@Path("id") _id:String?): Call<ProductbyIdResponeseModel>
}