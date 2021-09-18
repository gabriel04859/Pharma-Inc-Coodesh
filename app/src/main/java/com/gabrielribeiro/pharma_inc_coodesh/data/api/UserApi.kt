package com.gabrielribeiro.pharma_inc_coodesh.data.api

import com.gabrielribeiro.pharma_inc_coodesh.data.model.ResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("api/")
    suspend fun getUsers(
        @Query("results") results: Int,
        @Query("nat") nationality: String,
        @Query("gender") gender: String
    ): Response<ResultResponse>
}