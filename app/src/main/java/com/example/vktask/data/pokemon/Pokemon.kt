package com.example.vktask.data.pokemon

data class Pokemon(
    val abilities: List<com.example.vktask.data.pokemon.Ability>,
    val baseExperience: Int,
    val cries: com.example.vktask.data.pokemon.Cries,
    val forms: List<com.example.vktask.data.pokemon.Form>,
    val gameIndices: List<com.example.vktask.data.pokemon.GameIndice>,
    val height: Int,
    val heldItems: List<com.example.vktask.data.pokemon.HeldItem>,
    val id: Int,
    val isDefault: Boolean,
    val locationAreaEncounters: String,
    val moves: List<com.example.vktask.data.pokemon.Move>,
    val name: String,
    val order: Int,
    val pastAbilities: List<Any>,
    val pastTypes: List<Any>,
    val species: com.example.vktask.data.pokemon.Species,
    val sprites: com.example.vktask.data.pokemon.Sprites,
    val stats: List<com.example.vktask.data.pokemon.Stat>,
    val types: List<com.example.vktask.data.pokemon.Type>,
    val weight: Int
)