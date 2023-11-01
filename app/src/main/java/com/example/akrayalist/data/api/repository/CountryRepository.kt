package com.example.akrayalist.data.model.api.repository

import androidx.lifecycle.LiveData
import com.example.akrayalist.utils.Resource

interface CountryRepository {
    suspend fun getCountry() : Resource<CountriesModel>
}