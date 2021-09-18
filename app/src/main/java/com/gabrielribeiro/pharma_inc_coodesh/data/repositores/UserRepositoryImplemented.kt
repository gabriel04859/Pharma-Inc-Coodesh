package com.gabrielribeiro.pharma_inc_coodesh.data.repositores

import com.gabrielribeiro.pharma_inc_coodesh.data.api.UserApi
import com.gabrielribeiro.pharma_inc_coodesh.data.model.ResultResponse
import com.gabrielribeiro.pharma_inc_coodesh.utils.Resource

class UserRepositoryImplemented(private val api : UserApi) : UserRepository {
    override suspend fun getUsers(limit: Int, nationality: String, gender: String): Resource<ResultResponse> {
        return try {
            val response = api.getUsers(limit, nationality, gender)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Failure(null, "An unknown error occured")
            } else {
                Resource.Failure(null , "An unknown error occured")
            }
        } catch (e : Exception) {
            Resource.Failure(e , e.message)
        }
    }

}