package com.aldana.jhersin.poketinder

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PokemonApi{
    @GET("/api/v2/pokemon")
    suspend fun  getPokemons(): Response<PokemonListResponse>

    @POST("/auth/pokemon")
    suspend fun loginUser(@Body pokemon: User):Response<PokemonApi>
}

data class User (
    val userName: String,
    val password: String
)