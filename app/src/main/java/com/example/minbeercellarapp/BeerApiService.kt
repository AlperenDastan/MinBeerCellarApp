package com.example.minbeercellarapp

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BeerApiService {
    @GET("/api/Beers")
    suspend fun getAllBeers(): List<Beer>
    @GET("/api/Beers/{user}")
    suspend fun getUserBeers(@Path("user") user: String, @Query("orderBy") orderBy: String): List<Beer>

    @POST("/api/Beers")
    suspend fun addBeer(@Body newBeer: Beer): Beer

    @PUT("/api/Beers/{id}")
    suspend fun updateBeer(@Path("id") id: Int, @Body beer: Beer): Beer

    @DELETE("/api/Beers/{id}")
    suspend fun deleteBeer(@Path("id") id: Int): Response<Unit> // Change the return type if your API returns something different
}