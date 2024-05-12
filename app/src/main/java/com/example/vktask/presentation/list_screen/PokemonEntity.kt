package com.example.vktask.presentation.list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.vktask.data.models.PokemonListEntity

@Composable
fun PokemonEntity(
    entity: PokemonListEntity,
    navController: NavController,
    modifier: Modifier = Modifier,
    //viewModel: PokemonListViewModel = hiltViewModel()
) {

    Box(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .aspectRatio(1f)
            .background(MaterialTheme.colorScheme.surface)
            .border(8.dp, MaterialTheme.colorScheme.primary)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                navController.navigate("selected_pokemon_screen/${entity.name}")
            }

    ) {
        Column {
            SubcomposeAsyncImage(
                model = entity.imageUrl,
                contentDescription = entity.name,
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(0.5f)
                    )
                },
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)
            )
//            AsyncImage(
//                model = entity.imageUrl,
//                contentDescription = entity.name,
//                modifier = Modifier
//                    .size(120.dp)
//                    .align(Alignment.CenterHorizontally)
//            )
            Text(
                text = entity.name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}