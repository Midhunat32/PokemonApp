package com.globallogic.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.globallogic.app.data.Pokemon
import com.globallogic.app.data.PokemonImage

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    private var _pokemonLiveData = MutableLiveData<Pokemon>()
    private var _imageMutableLivedata = MutableLiveData<List<PokemonImage>>()
    private var imageLivedata:LiveData<List<PokemonImage>> = _imageMutableLivedata
    private var imageList = ArrayList<PokemonImage>()
    var pokemonLiveData :LiveData<Pokemon> = _pokemonLiveData

    suspend fun getGetRandomData() {
        val randomNumber = (1..800).random()
        val response = repository.getRandomPokemon(randomNumber)
        if (response.isSuccessful) {
            val pokemonData = response.body()!!
            _pokemonLiveData.postValue(pokemonData)
            setImageData(pokemonData)
        } else {
            _pokemonLiveData.postValue(null)
            setImageData(null)
            println("Failure>>>")
        }
    }

    private fun setImageData(pokemonData: Pokemon?) {
        imageList.clear()
        imageList.add(PokemonImage(pokemonData?.sprites!!.front_default))
        imageList.add(PokemonImage(pokemonData?.sprites!!.back_default))
        _imageMutableLivedata.postValue(imageList)
        imageLivedata = _imageMutableLivedata
    }

    fun getPokemonImages() : LiveData<List<PokemonImage>> {
       return imageLivedata
    }

}