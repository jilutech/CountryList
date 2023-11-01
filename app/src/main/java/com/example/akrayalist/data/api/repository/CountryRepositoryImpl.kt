package com.example.akrayalist.data.model.api.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.akrayalist.data.api.remote.CountriesAPI
import com.example.akrayalist.data.api.remote.RetrofitInstance
import com.example.akrayalist.utils.Resource

class CountryRepositoryImpl () : CountryRepository{
    override suspend fun getCountry(): Resource<CountriesModel> {
        return getCountryList()
    }

    private suspend fun getCountryList(): Resource<CountriesModel> {

        try {
            val response = RetrofitInstance.api.getCountry()
            if (response.isSuccessful && response.body() != null) {
                return Resource.Success(response.body()!!)
            } else if (response.errorBody() != null) {
                // Handle error response
                return Resource.Error("Error occurred", null)
            }
        } catch (e: Exception) {
            // Handle network or parsing exceptions
            return  Resource.Error("${e.message}Network error", null)
        }
        return Resource.Error("Un Expected Error occurred", null)
    }
}