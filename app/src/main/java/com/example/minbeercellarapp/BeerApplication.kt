package com.example.minbeercellarapp

import android.app.Application

class BeerApplication : Application() {
    val repository by lazy {
        BeerRepository(RetrofitInstance.api)
    }
}