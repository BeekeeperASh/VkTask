package com.example.vktask.presentation.detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vktask.data.pokemon.Pokemon
import com.example.vktask.domain.PokemonDetailViewModel
import com.example.vktask.tools.Resource

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {

    val pokemonDetails = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonDetails(pokemonName)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {

        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )

        PokemonStates(
            pokemonDetails = pokemonDetails,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 220.dp / 2f, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .shadow(12.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(top = 220.dp / 2f, start = 16.dp, end = 16.dp, bottom = 16.dp)
        )

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {

            if (pokemonDetails is Resource.Success) {
                pokemonDetails.data?.sprites.let {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.offset(y = 20.dp)
                    ) {
                        AsyncImage(
                            model = it!!.front_default,
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                            //.offset(y = 20.dp)
                        )
                        AsyncImage(
                            model = it.back_default,
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                            //.offset(y = 20.dp)
                        )
                    }


                }
            }

        }

    }

}












