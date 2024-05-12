package com.example.vktask.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.vktask.data.pokemon.Pokemon
import com.example.vktask.data.pokemon.Type
import com.example.vktask.domain.PokemonDetailViewModel
import com.example.vktask.tools.Resource
import okhttp3.internal.format
import java.util.Locale
import kotlin.math.round

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
                            contentDescription = pokemonName,
                            modifier = Modifier
                                .size(200.dp)
                            //.offset(y = 20.dp)
                        )
                        AsyncImage(
                            model = it!!.back_default,
                            contentDescription = pokemonName,
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

@Composable
fun PokemonDetailTopSection(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

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
    }

}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .border(4.dp, MaterialTheme.colorScheme.scrim)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .height(36.dp)
            ) {
                Text(
                    text = type.type.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

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

@Composable
fun PokemonDataItem(
    modifier: Modifier = Modifier,
    dataType: String,
    dataValue: Float,
    dataIcon: ImageVector
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {

        Icon(
            imageVector = dataIcon,
            contentDescription = dataType,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$dataValue $dataType",
            color = MaterialTheme.colorScheme.onBackground
        )

    }

}

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

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {

    var isAnimationComplete by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (isAnimationComplete) {
            statValue / statMaxValue.toFloat()
        } else 0f, label = "",
        animationSpec = tween(
            animDuration,
            animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        isAnimationComplete = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.outline)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercentage.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {

            Text(text = statName, fontWeight = FontWeight.Bold)
            Text(
                text = (curPercentage.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )

        }
    }

}