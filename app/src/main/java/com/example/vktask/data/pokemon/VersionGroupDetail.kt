package com.example.vktask.data.pokemon

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: com.example.vktask.data.pokemon.MoveLearnMethod,
    val version_group: com.example.vktask.data.pokemon.VersionGroup
)