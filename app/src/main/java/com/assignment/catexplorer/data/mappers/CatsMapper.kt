package com.assignment.catexplorer.data.mappers

import com.assignment.catexplorer.data.local.CatBreedEntity
import com.assignment.catexplorer.data.remote.models.CatBreed

fun CatBreed.toCatBreedEntity(): CatBreedEntity {
    return CatBreedEntity(
        id = id,
        name = name,
        description = description,
        imageUrl = image?.url,
        lifeSpan = lifeSpan,
        origin = origin,
        temperament = temperament,
        childFriendly = childFriendly,
        intelligence = intelligence,
        affectionLevel = affectionLevel
    )
}