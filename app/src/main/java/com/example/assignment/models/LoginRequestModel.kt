package com.example.assignment.models

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequestModel (
    @JsonProperty ("email") val email:String?,
    @JsonProperty ("password") val password:String?
)