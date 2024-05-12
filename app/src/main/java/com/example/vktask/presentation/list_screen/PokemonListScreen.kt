package com.example.vktask.presentation.list_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vktask.domain.PokemonListViewModel

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {

        Column {
            Spacer(modifier = Modifier.height(24.dp))
            SearchBar(
                hint = "Введите имя покемона",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                viewModel.searchPokemon(it)
            }
//            PokemonEntity(
//                entity = PokemonListEntity(
//                    "Test Pokemon",
//                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png",
//                    0
//                ),
//                navController = navController,
//                modifier = Modifier.padding(16.dp)
//            )
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController)
        }

    }

}







