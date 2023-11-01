package com.example.akrayalist.ui.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.akrayalist.ListApplication
import com.example.akrayalist.data.model.api.repository.CountriesModel
import com.example.akrayalist.data.model.api.repository.CountryRepository
import com.example.akrayalist.utils.Resource
import kotlinx.coroutines.launch
import okio.IOException
import kotlin.math.log

class HomeViewModel(
    app: Application,
    val countryRepository: CountryRepository
) : AndroidViewModel(app) {

    private var _countryList = MutableLiveData<Resource<CountriesModel>>()
    val countryList : LiveData<Resource<CountriesModel>> = _countryList

    init {
        getData()
    }

    private fun getData() {
        _countryList.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                viewModelScope.launch {
                    _countryList.postValue(countryRepository.getCountry())
                }
            } else {
                _countryList.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> _countryList.postValue(Resource.Error("Network Failure"))
                else -> _countryList.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<ListApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return false
    }

}