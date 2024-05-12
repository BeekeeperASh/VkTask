package com.example.vktask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vktask.presentation.detail_screen.PokemonDetailScreen
import com.example.vktask.presentation.list_screen.PokemonListScreen
import com.example.vktask.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "all_pokemon_screen") {
                    composable("all_pokemon_screen") {
                        PokemonListScreen(navController = navController)
                    }
                    composable(
                        "selected_pokemon_screen/{name}",
                        arguments = listOf(
                            navArgument("name") {
                                type = NavType.StringType
                                this.defaultValue = "Pikachu"
                            }
                        )
                    ) {
                        val name = remember {
                            it.arguments?.getString("name")
                        }
                        PokemonDetailScreen(pokemonName = name?.lowercase(Locale.ROOT) ?: "", navController = navController)
                    }
                }
            }
        }
    }
}