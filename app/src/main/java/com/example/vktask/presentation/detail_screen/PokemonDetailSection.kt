package com.example.vktask.presentation.detail_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vktask.data.pokemon.Pokemon
import java.util.Locale

@Composable
fun PokemonDetailSection(
    pokemonDetails: Pokemon,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 200.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "pokemon id is ${pokemonDetails.id}, name is ${
                pokemonDetails.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
            }",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        PokemonTypeSection(types = pokemonDetails.types)
        PokemonDetailDataSection(weight = pokemonDetails.weight, height = pokemonDetails.height)
        PokemonBaseStats(pokemon = pokemonDetails)
        AudioPlaySection(audioUrl = pokemonDetails.cries.latest, modifier = Modifier.fillMaxWidth())
    }

}