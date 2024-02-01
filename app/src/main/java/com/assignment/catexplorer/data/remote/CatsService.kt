package com.assignment.catexplorer.data.remote

import com.assignment.catexplorer.data.remote.models.CatBreed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatsService {

    companion object {
        const val BASE_URL = "https://api.thecatapi.com/v1/"
    }

    @Headers("x-api-key: live_46UHWCq6f8tFGN9s0Qtrbyx62HrHFl0qZ6iGMbS9SfC2eK2OZA8qpEeVIxfzD605")
    @GET("breeds")
    suspend fun getCatBreeds(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("order") order: String = "ASC"
    ): Response<List<CatBreed>>

}