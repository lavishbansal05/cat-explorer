package com.assignment.catexplorer.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.assignment.catexplorer.data.local.CatBreedEntity

@Dao
interface CatsDao {

    @Upsert
    suspend fun upsertCats(beers: List<CatBreedEntity>)

    @Query("SELECT * FROM cats_table")
    fun getCatsPagingSource(): PagingSource<Int, CatBreedEntity>

    @Query("DELETE FROM cats_table")
    suspend fun clearCats()
}