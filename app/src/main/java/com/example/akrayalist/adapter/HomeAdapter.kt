package com.example.akrayalist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.akrayalist.R
import com.example.akrayalist.data.model.api.repository.CountriesModelItem
import com.example.akrayalist.databinding.RvItemBinding


class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    private val callback = object : DiffUtil.ItemCallback<CountriesModelItem>(){
        override fun areItemsTheSame(oldItem: CountriesModelItem, newItem: CountriesModelItem): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: CountriesModelItem, newItem: CountriesModelItem): Boolean {
            return oldItem == newItem
        }

    }

    val diffUtil = AsyncListDiffer(this,callback)

    inner class HomeViewHolder(private val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindData(country: CountriesModelItem){
            binding.tvNameAndRegion.text = country.name + "," +country.region
            binding.tvCapital.text = country.capital
            binding.code.text = country.code
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount() =  diffUtil.currentList.size


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val breakingNewsItem = diffUtil.currentList[position]
        holder.bindData(breakingNewsItem)

    }
}