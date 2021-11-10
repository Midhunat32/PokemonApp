package com.globallogic.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.globallogic.app.data.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    private var _pokemonLiveData = MutableLiveData<Pokemon>()
    private var _imageMutableLivedata = MutableLiveData<List<String>>()
    private var imageLivedata:LiveData<List<String>> = _imageMutableLivedata
    private var imageList = ArrayList<String>()
    var pokemonLiveData :LiveData<Pokemon> = _pokemonLiveData

    /**
     * Method to fetch random pokemon data.
     */
     fun getGetRandomData() {
         viewModelScope.launch(Dispatchers.IO) {
             val randomNumber = (1..800).random()
             val response = repository.getRandomPokemon(randomNumber)
             if (response.isSuccessful) {
                 val pokemonData = response.body()!!
                 _pokemonLiveData.postValue(pokemonData)
                 setImageData(pokemonData)
             } else {
                 _pokemonLiveData.postValue(null)
                 setImageData(null)
             }
         }

    }

    /**
     * Method to set pokemon image data.
     */
    private fun setImageData(pokemonData: Pokemon?) {
        imageList.clear()
        pokemonData?.let {
            imageList.add(it.sprites.front_default?:"")
            imageList.add(it.sprites.back_default?:"")
        }
        _imageMutableLivedata.postValue(imageList)
        imageLivedata = _imageMutableLivedata
    }


    /**
     * Method to return pokemon image list
     * @return LiveData of image list
     */
    fun getPokemonImages() : LiveData<List<String>> {
       return imageLivedata
    }

}