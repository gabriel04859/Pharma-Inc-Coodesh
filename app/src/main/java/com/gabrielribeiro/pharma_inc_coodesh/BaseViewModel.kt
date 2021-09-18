package com.gabrielribeiro.pharma_inc_coodesh

import androidx.lifecycle.*
import com.gabrielribeiro.pharma_inc_coodesh.data.model.ResultResponse
import com.gabrielribeiro.pharma_inc_coodesh.data.repositores.UserRepository
import com.gabrielribeiro.pharma_inc_coodesh.utils.Resource
import kotlinx.coroutines.launch

class BaseViewModel(private val repository : UserRepository) : ViewModel() {
    private var _resultResponse = MutableLiveData<Resource<ResultResponse>>()
    val resultResponse : LiveData<Resource<ResultResponse>> get() = _resultResponse

    fun getUser(limit : Int = 50,  nationality: String = "", gender: String = "") {
        _resultResponse.value = Resource.Loading()
        viewModelScope.launch {
            _resultResponse.value = repository.getUsers(limit, nationality, gender)
        }
    }

    class BaseViewModelFactory (private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BaseViewModel(repository) as T
        }

    }

}