package com.example.vktask.domain

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vktask.data.models.PokemonListEntity
import com.example.vktask.data.pokemon.PokemonList
import com.example.vktask.repository.PokemonRepository
import com.example.vktask.tools.Constants.PAGE_NUM
import com.example.vktask.tools.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPokId = 0

    var pokemonList = mutableStateOf<List<PokemonListEntity>>(listOf())
    var errorResult = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var isEnd = mutableStateOf(false)

    private var storedPokemonList = listOf<PokemonListEntity>()
    var isSearching = mutableStateOf(false)
    private var initialSearchState = true

    init {
        pokemonListPaginationLoading()
    }

    fun searchPokemon(pokemonName: String){
        val currentList = if (initialSearchState){
            pokemonList.value
        } else {
            storedPokemonList
        }

        viewModelScope.launch(Dispatchers.Default) {

            if (pokemonName.isEmpty()){
                pokemonList.value = storedPokemonList
                initialSearchState = true
                isSearching.value = false
                return@launch
            }

            val result = currentList.filter {
                it.name.contains(pokemonName.trim(), ignoreCase = true)
            }

            if (initialSearchState) {
                storedPokemonList = pokemonList.value
                initialSearchState = false
            }

            pokemonList.value = result
            isSearching.value = true
        }
    }

    fun pokemonListPaginationLoading() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonList(PAGE_NUM, currentPokId * PAGE_NUM)
            when (result) {
                is Resource.Success -> {
                    isEnd.value = currentPokId * PAGE_NUM >= result.data!!.count
                    val pokemonEntities = result.data.results.mapIndexed { index, entity ->
                        val number = if (entity.url.endsWith("/")) {
                            entity.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entity.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokemonListEntity(entity.name.capitalize(Locale.ROOT), url, number.toInt())
                    }
                    currentPokId += 1
                    isLoading.value = false
                    errorResult.value = ""
                    pokemonList.value += pokemonEntities
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    errorResult.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

}