package com.example.assignment.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Objects



data class ProductModel(
    @JsonProperty("_id") val id: String?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("description") val  description: String?,
    @JsonProperty("image") val  image: String?,
    @JsonProperty("rating") val  rating: Int?,
    @JsonProperty("voting") val  voting: Int?,
    @JsonProperty("category") val  category: String?,
    @JsonProperty("price") val  price: Int?,
)

data class ProductResponseModel(
    @JsonProperty("status") val status:Boolean?,
    @JsonProperty("products") val data:List<ProductModel>?
)
