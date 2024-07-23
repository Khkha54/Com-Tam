package com.example.assignment.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.android.gms.common.internal.Objects

data class UserModel(
    @JsonProperty("_id") val _id:String?,
    @JsonProperty("name") val name:String?,
    @JsonProperty("email") val email:String?,
    @JsonProperty("carts") val carts:Any?,
    @JsonProperty("__v") val __v:Int?,
)

data class LoginResponseModel (
    @JsonProperty ("status") val status:Boolean?,
    @JsonProperty ("user") val user:UserModel?
)