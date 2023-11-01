package com.example.akrayalist.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.akrayalist.data.model.api.repository.CountryRepository

class HomeViewModelProviderFactory(
    val app: Application,
    val newsRepository: CountryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(app,newsRepository) as T
    }
}