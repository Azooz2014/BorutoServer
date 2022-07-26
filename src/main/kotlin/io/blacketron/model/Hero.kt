package io.blacketron.model

import kotlinx.serialization.Serializable

@Serializable
data class Hero(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val about: String,
    val rating: Double,
    val power: Int,
    val birthMonth: String,
    val birthDay: String,
    val family: List<String>,
    val abilities: List<String>,
    val natureTypes: List<String>
)
