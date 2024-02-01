package com.assignment.catexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CatBreedEntity::class],
    version = 1,
    exportSchema = false //TODO: LAVISH remove
)
abstract class CatsDatabase : RoomDatabase() {
    abstract val dao: CatsDao
}