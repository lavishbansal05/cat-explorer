package com.assignment.catexplorer.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CatsDao {

    @Upsert
    suspend fun upsertCats(beers: List<CatBreedDBEntity>)

    @Query("SELECT * FROM cats_table")
    fun getCatsPagingSource(): PagingSource<Int, CatBreedDBEntity>

    @Query("DELETE FROM cats_table")
    suspend fun clearCats()

    @Query("SELECT * FROM cats_table WHERE id = :id  LIMIT 1")
    suspend fun getCatBreedDetail(id: String): CatBreedDBEntity

    @Query("SELECT MAX(modifiedAt) FROM cats_table")
    suspend fun getLastModifiedTS() : Long?
}