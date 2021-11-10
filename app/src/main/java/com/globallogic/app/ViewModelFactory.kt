package com.globallogic.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory constructor(private val repository: PokemonRepository) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            PokemonViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}