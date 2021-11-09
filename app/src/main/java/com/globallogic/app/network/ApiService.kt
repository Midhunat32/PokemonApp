package com.globallogic.app.network

import com.globallogic.app.data.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("pokemon/{number}")
    suspend fun getRandomPokemon(@Path("number") number:Int) : Response<Pokemon>
}