package com.assignment.catexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CatBreedDBEntity::class],
    version = 1,
)
abstract class CatsDatabase : RoomDatabase() {
    abstract val dao: CatsDao
}