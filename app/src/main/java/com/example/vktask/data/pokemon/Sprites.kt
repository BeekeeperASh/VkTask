package com.example.vktask.data.pokemon

data class Sprites(
    val back_default: String,
    val back_female: String,
    val back_shiny: String,
    val back_shiny_female: String,
    val front_default: String,
    val front_female: String,
    val front_shiny: String,
    val front_shiny_female: String,
    val other: com.example.vktask.data.pokemon.Other,
    val versions: com.example.vktask.data.pokemon.Versions
)