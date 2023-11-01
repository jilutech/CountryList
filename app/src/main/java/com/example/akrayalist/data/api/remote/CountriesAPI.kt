package com.example.akrayalist.data.api.remote

import com.example.akrayalist.data.model.api.repository.CountriesModel
import com.example.akrayalist.data.model.api.repository.CountriesModelItem
import retrofit2.Response
import retrofit2.http.GET

interface CountriesAPI {

    @GET("/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
    suspend fun getCountry(): Response<CountriesModel>


}