package com.example.paging3.presentation.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.paging3.data.local.TodoEntity
import com.example.paging3.data.mappers.toTodo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ShowTodosViewModel @Inject constructor(
    pager : Pager<Int,TodoEntity>
) : ViewModel() {
    val todosPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toTodo() }
        }.cachedIn(viewModelScope)
}