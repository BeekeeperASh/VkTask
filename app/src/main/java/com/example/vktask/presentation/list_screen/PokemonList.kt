package com.example.vktask.presentation.list_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vktask.domain.PokemonListViewModel

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

    val pokemonList by remember {
        viewModel.pokemonList
    }
    val errorResult by remember {
        viewModel.errorResult
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    val isEnd by remember {
        viewModel.isEnd
    }
    val isSearching by remember {
        viewModel.isSearching
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp)
    ) {

        items(pokemonList.size) {
            if (it >= pokemonList.size - 1 && !isLoading && !isSearching && !isEnd) {
                viewModel.pokemonListPaginationLoading()
            }
            PokemonEntity(
                entity = pokemonList[it],
                navController = navController,
                modifier = Modifier.padding(16.dp)
            )
        }

    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        if (isLoading){
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if (errorResult.isNotEmpty()){
            ErrorSection(error = errorResult) {
                viewModel.pokemonListPaginationLoading()
            }
        }
    }


}