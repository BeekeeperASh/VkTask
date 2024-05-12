package com.example.vktask.presentation.detail_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vktask.data.pokemon.Pokemon


@Composable
fun PokemonBaseStats(pokemon: Pokemon) {
    val maxBaseMaxStat = remember {
        pokemon.stats.maxOf {
            it.base_stat
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Pokemon base stats",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        for (i in pokemon.stats.indices) {
            val stats = pokemon.stats[i]
            PokemonStat(
                statName = stats.stat.name,
                statValue = stats.base_stat,
                statMaxValue = maxBaseMaxStat,
                statColor = when (stats.stat.name) {
                    "hp" -> Color.Green
                    "attack" -> Color.Red
                    "defense" -> Color.LightGray
                    "special-attack" -> Color.Magenta
                    "special-defense" -> Color.Cyan
                    "speed" -> Color.Yellow
                    else -> Color.Black
                },
                animDelay = i * 100
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}