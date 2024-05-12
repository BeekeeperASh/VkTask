package com.example.vktask.domain

import androidx.lifecycle.ViewModel
import com.example.vktask.data.pokemon.Pokemon
import com.example.vktask.repository.PokemonRepository
import com.example.vktask.tools.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    suspend fun getPokemonDetails(pokemonName: String): Resource<Pokemon>{
        return repository.getPokemon(pokemonName)
    }

}