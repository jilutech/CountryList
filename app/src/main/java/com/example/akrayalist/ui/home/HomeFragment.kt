package com.example.akrayalist.ui.home

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.akrayalist.ListApplication
import com.example.akrayalist.adapter.HomeAdapter
import com.example.akrayalist.data.model.api.repository.CountryRepository
import com.example.akrayalist.data.model.api.repository.CountryRepositoryImpl
import com.example.akrayalist.databinding.FragmentHomeBinding
import com.example.akrayalist.utils.Resource
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    lateinit var viewModel: HomeViewModel
    lateinit var homeAdapter: HomeAdapter
    var isLoading = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val newsRepository = CountryRepositoryImpl()
        val viewModelProviderFactory = HomeViewModelProviderFactory(requireActivity().application,newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeAdapter = HomeAdapter()
        val root: View = binding.root

        binding.rvCountryList.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvCountryList.adapter = homeAdapter
        viewModel.countryList.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Error ->{
                    hideProgressBar()
                    Snackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    homeAdapter.diffUtil.submitList(response.data)
                }
            }
        })

        return root
    }
    private fun hideProgressBar() {
        _binding!!.paginationProgressBar.visibility  = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        _binding!!.paginationProgressBar.visibility  = View.VISIBLE
        isLoading = true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}