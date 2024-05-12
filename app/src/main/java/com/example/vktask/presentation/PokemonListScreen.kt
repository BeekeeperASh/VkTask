package com.example.vktask.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.vktask.data.models.PokemonListEntity
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

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var showHint by remember {
        mutableStateOf(true)
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .onFocusChanged {
                    showHint = !it.isFocused // && text.isEmpty()
                }
        )
        if (showHint) {
            Text(
                text = hint,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            )
        }
    }

}

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

@Composable
fun PokemonRow(
    navController: NavController,
    rowIndex: Int,
    entities: List<PokemonListEntity>
) {

    Column {
        Row {

        }
        Spacer(modifier = Modifier.height(20.dp))
    }

}

@Composable
fun ErrorSection(
    error: String,
    onReload: () -> Unit
) {
    Column {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onReload()
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
            ,
        ) {
            Text(text = "Reload")
        }
    }
}
