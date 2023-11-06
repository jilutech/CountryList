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

            return if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Resource.Success(responseBody)
                } else {
                    // Handle null response body
                    Resource.Error("Null response body", null)
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = errorBody ?: response.message()
                Resource.Error("Error: ${response.code()} - $errorMessage", null)
            }
        } catch (e: Exception) {
            return Resource.Error("Network error: ${e.message}", null)
        }
    }

}