package com.example.assignment.models

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterResponseModel (
    @JsonProperty("status") val  password:Boolean?
)