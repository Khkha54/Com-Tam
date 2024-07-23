package com.example.assignment.models

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterRequestModel (
    @JsonProperty("name") val  name:String?,
    @JsonProperty("email") val  email:String?,
    @JsonProperty("password") val  password:String?
)