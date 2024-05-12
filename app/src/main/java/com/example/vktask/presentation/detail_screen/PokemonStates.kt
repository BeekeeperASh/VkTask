package com.example.vktask.presentation.detail_screen

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.vktask.data.pokemon.Pokemon
import com.example.vktask.tools.Resource

@Composable
fun PokemonStates(
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    pokemonDetails: Resource<Pokemon>
) {
    when (pokemonDetails) {
        is Resource.Success -> {
            PokemonDetailSection(
                pokemonDetails = pokemonDetails.data!!,
                //modifier = modifier.offset( y = (-20).dp)
            )
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }

        is Resource.Error -> {
            Text(
                text = pokemonDetails.message!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.displayMedium,
                modifier = modifier,
                textAlign = TextAlign.Center
            )
        }
    }
}