package com.example.minbeercellarapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BeerViewModel(private val repository: BeerRepository) : ViewModel() {
    val beers = MutableLiveData<List<Beer>?>()
    val error = MutableLiveData<String>()

    fun getUserBeers(userEmail: String, orderBy: String = "") {
        viewModelScope.launch {
            try {
                val userBeers = repository.getUserBeers(userEmail, orderBy)
                beers.postValue(userBeers)
            } catch (e: Exception) {
                error.postValue(e.message)
            }
        }
    }

    fun addBeer(beer: Beer) {
        viewModelScope.launch {
            try {
                val newBeer = repository.addBeer(beer)
                // Assuming you want to update the live data with the new list including the added beer
                beers.value = beers.value?.plus(newBeer)
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

    fun sortBeers(criteria: String) {
        val sortedList = when (criteria) {
            "Brewery" -> beers.value?.sortedBy { it.brewery }
            "Name" -> beers.value?.sortedBy { it.name }
            "ABV" -> beers.value?.sortedBy { it.abv }
            else -> beers.value
        }
        beers.postValue(sortedList)
    }

    fun filterBeers(filter: String) {
        val filteredList = beers.value?.filter {
            it.brewery.contains(filter, ignoreCase = true) ||
                    it.name.contains(filter, ignoreCase = true) ||
                    it.style.contains(filter, ignoreCase = true)
        }
        beers.postValue(filteredList)
    }


    fun deleteBeer(id: Int) {
        viewModelScope.launch {
            val response = repository.deleteBeer(id)
            if (response.isSuccessful) {
                beers.value = beers.value?.filter { it.id != id }
            } else {
                error.postValue("Failed to delete beer: ${response.errorBody()?.string()}")
            }
        }
    }

}