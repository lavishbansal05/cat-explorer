package com.assignment.catexplorer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats_table")
data class CatBreedEntity(
    @PrimaryKey
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
    var modifiedAt: Long? = null
)