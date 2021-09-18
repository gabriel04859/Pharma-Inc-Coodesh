package com.gabrielribeiro.pharma_inc_coodesh.data.model

import com.google.gson.annotations.SerializedName

data class ResultResponse(
    val info: Info,
    @SerializedName("results")
    val userResponses: List<UserResponse>
)