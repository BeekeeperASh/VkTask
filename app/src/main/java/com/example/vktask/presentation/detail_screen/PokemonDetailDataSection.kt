package com.example.vktask.presentation.detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PokemonDetailDataSection(
    weight: Int,
    height: Int,
) {

    val pokemonWeight = remember {
        weight / 10f
    }
    val pokemonHeight = remember {
        height / 10f
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PokemonDataItem(
            dataType = "kg", dataValue = pokemonWeight, dataIcon = Icons.Default.Scale,
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier
                .size(1.dp, 24.dp)
                .background(MaterialTheme.colorScheme.scrim)
        )
        PokemonDataItem(
            dataType = "m", dataValue = pokemonHeight, dataIcon = Icons.Default.Height,
            modifier = Modifier.weight(1f)
        )
    }

}