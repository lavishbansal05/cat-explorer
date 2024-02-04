package com.assignment.catexplorer.data.mappers.domain

import com.assignment.catexplorer.data.local.CatBreedDBEntity
import com.assignment.catexplorer.domain.model.CatBreedEntity

fun CatBreedDBEntity.toCatBreedEntity(): CatBreedEntity {
    return CatBreedEntity(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        lifeSpan = lifeSpan,
        origin = origin,
        temperament = temperament,
        childFriendly = childFriendly,
        intelligence = intelligence,
        affectionLevel = affectionLevel
    )
}
