package com.gabrielribeiro.pharma_inc_coodesh.data.repositores


import com.gabrielribeiro.pharma_inc_coodesh.data.model.ResultResponse
import com.gabrielribeiro.pharma_inc_coodesh.utils.Resource

interface UserRepository {
    suspend fun getUsers(limit : Int, nationality : String, gender : String) : Resource<ResultResponse>
}