package com.assignment.catexplorer.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats_table")
data class CatBreedDBEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: String,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("image_url") val imageUrl: String?,
    @ColumnInfo("life_span") val lifeSpan: String?,
    @ColumnInfo("origin") val origin: String?,
    @ColumnInfo("temperament") val temperament: String?,
    @ColumnInfo("child_friendly") val childFriendly: Int?,
    @ColumnInfo("intelligence") val intelligence: Int?,
    @ColumnInfo("affectionLevel") val affectionLevel: Int?,
    @ColumnInfo("modifiedAt") var modifiedAt: Long? = null,
    @ColumnInfo("suppressedTail") val suppressedTail: Int?,
    @ColumnInfo("short_legs") val shortLegs: Int?,
    @ColumnInfo("wikipedia_url") val wikipediaUrl: String?,
    @ColumnInfo("hypo_allergenic") val hypoallergenic: Int?,
    @ColumnInfo("reference_image_id") val referenceImageId: String?,
)
