package com.example.paging3.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TodoApi {
    @GET("/todos")
    suspend fun getTodos(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<TodoDto>
}