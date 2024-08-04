package com.example.paging3.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TodoDao {

    @Upsert
    suspend fun upsertTodos(todos: List<TodoEntity>)

    @Query("SELECT * FROM todoentity")
    fun pageSource(): PagingSource<Int, TodoEntity>

    @Query("DELETE FROM todoentity")
    fun clearAll()



}