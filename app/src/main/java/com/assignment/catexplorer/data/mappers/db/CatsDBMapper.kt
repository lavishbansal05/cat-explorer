package com.assignment.catexplorer.data.mappers.db

import com.assignment.catexplorer.data.local.CatBreedDBEntity
import com.assignment.catexplorer.data.remote.models.CatBreed

fun CatBreed.toCatBreedDBEntity(): CatBreedDBEntity {
    return CatBreedDBEntity(
        id = id,
        name = name,
        description = description,
        imageUrl = image?.url,
        lifeSpan = lifeSpan,
        origin = origin,
        temperament = temperament,
        childFriendly = childFriendly,
        intelligence = intelligence,
        affectionLevel = affectionLevel,
        suppressedTail = suppressedTail,
        shortLegs = shortLegs,
        wikipediaUrl = wikipediaUrl,
        hypoallergenic = hypoallergenic,
        referenceImageId = referenceImageId

    )
}
