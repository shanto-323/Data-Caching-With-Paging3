package com.example.paging3.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    val userId: Int,

    @PrimaryKey
    val id: Int,
    val title: String,
    val completed: Boolean
)
