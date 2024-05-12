package com.example.vktask.repository

import com.example.vktask.data.api.PokemonApi
import com.example.vktask.data.pokemon.Pokemon
import com.example.vktask.data.pokemon.PokemonList
import com.example.vktask.tools.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi
){

    suspend fun getPokemon(name: String): Resource<Pokemon>{
        val response = try {
            pokemonApi.getPokemon(name)
        } catch (e: Exception){
            return Resource.Error(message = "Error")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>{
        val response = try {
            pokemonApi.getPokemonList(limit, offset)
        } catch (e: Exception){
            return Resource.Error(message = "Error")
        }
        return Resource.Success(response)
    }

}