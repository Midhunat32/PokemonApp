package com.globallogic.app

import com.globallogic.app.network.ApiService

class PokemonRepository constructor(private val apiService: ApiService){

    suspend fun getRandomPokemon(randomNumber:Int) = apiService.getRandomPokemon(randomNumber)
}