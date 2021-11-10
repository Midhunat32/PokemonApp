package com.globallogic.app

import com.globallogic.app.network.ApiService

class PokemonRepository constructor(private val apiService: ApiService){
    /**
     * Method to fetch random pokemon data from server.
     * @param randomNumber a random number.
     */
    suspend fun getRandomPokemon(randomNumber:Int) = apiService.getRandomPokemon(randomNumber)
}