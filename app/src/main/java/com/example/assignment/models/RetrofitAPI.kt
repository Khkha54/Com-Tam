package com.example.assignment.models

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitAPI {
    private val retrofit = RetrofitClient.getClient()
    private val retrofitInterface = retrofit.create(IRetrofit::class.java)

    fun register(body: RegisterRequestModel,
                 callback: (RegisterResponseModel?) -> Unit){
        retrofitInterface.register(body).enqueue(
            object : Callback<RegisterResponseModel> {
                override fun onResponse(call: Call<RegisterResponseModel>,
                                        response: Response<RegisterResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val registerResponseModel = response.body()
                        Log.d(">>>resister",registerResponseModel.toString())
                        callback(registerResponseModel)
                    } else {
                        Log.d(">>>Failed to register", response.message())
                    }
                }

                override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                    Log.d(">>>Failed to register", t.message ?: "Unknown error")
                }
            })
    }

    fun login(body: LoginRequestModel,
                 callback: (LoginResponseModel?) -> Unit){
        retrofitInterface.login(body).enqueue(
            object : Callback<LoginResponseModel> {
                override fun onResponse(call: Call<LoginResponseModel>,
                                        response: Response<LoginResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val loginResponseModel = response.body()
                        Log.d(">>>login",loginResponseModel?.user.toString())
                        callback(loginResponseModel)
                    } else {
                        Log.d(">>>Failed to login", response.message())
                    }
                }

                override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                    Log.d("Failed to login", t.message ?: "Unknown error")
                }
            })
    }

    fun getProducts(category:String?,
              callback: (ProductResponseModel?) -> Unit){
        retrofitInterface.getProduct(category).enqueue(
            object : Callback<ProductResponseModel> {
                override fun onResponse(call: Call<ProductResponseModel>,
                                        response: Response<ProductResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d(">>>pro",result?.data.toString())
                        callback(result)
                    } else {
                        Log.d(">>>Failed to getProducts", response.message())
                    }
                }

                override fun onFailure(call: Call<ProductResponseModel>, t: Throwable) {
                    Log.d(">>>Failed to getProducts", t.message ?: "Unknown error")
                }
            })
    }

    fun getProductbyId(_id:String?,
                    callback: (ProductbyIdResponeseModel?) -> Unit){
        retrofitInterface.getProductById(_id).enqueue(
            object : Callback<ProductbyIdResponeseModel> {
                override fun onResponse(call: Call<ProductbyIdResponeseModel>,
                                        response: Response<ProductbyIdResponeseModel>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d(">>>pro",result?.product?.name.toString())
                        callback(result)
                    } else {
                        Log.d(">>>Failed to getProducts", response.message())
                    }
                }

                override fun onFailure(call: Call<ProductbyIdResponeseModel>, t: Throwable) {
                    Log.d(">>>Failed to getProducts", t.message ?: "Unknown error")
                }
            })
    }
}