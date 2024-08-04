package com.example.paging3.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paging3.data.local.TodoDatabase
import com.example.paging3.data.local.TodoEntity
import com.example.paging3.data.mappers.toTodoEntity

@OptIn(ExperimentalPagingApi::class)
class TodoRemoteMediator(
    private val todoApi: TodoApi, private val todoDatabase: TodoDatabase
) : RemoteMediator<Int, TodoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TodoEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val todos = todoApi.getTodos(
                page = loadKey,
                limit = state.config.pageSize
            )

            todoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH){
                    todoDatabase.dao.clearAll()
                }
                val todoEntities = todos.map { it.toTodoEntity() }
                todoDatabase.dao.upsertTodos(todoEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = todos.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}