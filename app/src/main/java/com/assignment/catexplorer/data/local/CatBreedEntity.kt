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
)