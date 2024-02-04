package com.assignment.catexplorer.domain.model

data class CatBreedEntity(
    val id: String,
    val name: String?,
    val description: String?,
    val imageUrl: String?,
    val lifeSpan: String?,
    val origin: String?,
    val temperament: String?,
    val childFriendly: Int?,
    val intelligence: Int?,
    val affectionLevel: Int?,
)
