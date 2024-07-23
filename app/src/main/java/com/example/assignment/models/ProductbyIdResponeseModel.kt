package com.example.assignment.models

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductbyIdResponeseModel(
    @JsonProperty("status") val status:Boolean?,
    @JsonProperty("product") val product:ProductModel?
)
