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
                error.postValue("Failed to fetch beers: ${e.message}")
                Log.e("BeerViewModel", "Error fetching beers", e)
            }
        }
    }

    fun addBeer(beer: Beer) {
        viewModelScope.launch {
            try {
                val newBeer = repository.addBeer(beer)
                beers.value = beers.value?.plus(newBeer)
            } catch (e: Exception) {
                error.value = "Failed to add beer: ${e.message}"
                Log.e("BeerViewModel", "Error adding beer", e)
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

    fun updateBeer(beer: Beer) {
        viewModelScope.launch {
            try {
                val updatedBeer = repository.updateBeer(beer)
                beers.value = beers.value?.map {
                    if (it.id == beer.id) updatedBeer else it
                }
            } catch (e: Exception) {
                error.postValue("Failed to update beer: ${e.message}")
                Log.e("BeerViewModel", "Error updating beer", e)
            }
        }
    }
    fun deleteBeer(beerId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deleteBeer(beerId)
                if (response.isSuccessful) {
                    beers.value = beers.value?.filterNot { it.id == beerId }
                } else {
                    error.postValue("Failed to delete beer")
                    Log.e("BeerViewModel", "Error deleting beer: HTTP ${response.code()}")
                }
            } catch (e: Exception) {
                error.postValue("Exception during deletion: ${e.message}")
                Log.e("BeerViewModel", "Error deleting beer", e)
            }
        }
    }

}