package com.example.paging3.data.mappers

import com.example.paging3.data.local.TodoEntity
import com.example.paging3.data.remote.TodoDto
import com.example.paging3.domain.model.Todo

fun TodoDto.toTodoEntity(): TodoEntity {
    return TodoEntity(
        userId = userId,
        id = id,
        title = title,
        completed = completed
    )
}

fun TodoEntity.toTodo(): Todo {
    return Todo(
        userId = userId,
        id = id,
        title = title,
        completed = completed
    )
}


//What does this do