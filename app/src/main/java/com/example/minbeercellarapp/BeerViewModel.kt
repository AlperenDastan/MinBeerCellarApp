package com.example.minbeercellarapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BeerViewModel(private val repository: BeerRepository) : ViewModel() {
    val beers = MutableLiveData<List<Beer>>()
    val error = MutableLiveData<String>()

    fun getAllBeers() {
        viewModelScope.launch {
            try {
                beers.value = repository.getAllBeers()
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }


    fun getUserBeers(userId: String, orderBy: String = "") {
        viewModelScope.launch {
            try {
                val results = repository.getUserBeers(userId, orderBy)
                beers.postValue(results)
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

    fun updateBeer(beer: Beer) {
        viewModelScope.launch {
            try {
                val updatedBeer = repository.updateBeer(beer)
                // Handle the updated beer, e.g., update the list in LiveData
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

    fun deleteBeer(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteBeer(id)
                // Handle the deletion, e.g., remove the beer from the list in LiveData
            } catch (e: Exception) {
                error.value = e.message
                // Define methods for PUT and DELETE operations here when you have them in your API.
            }
        }
    }
}