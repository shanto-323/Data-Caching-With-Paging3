package com.example.paging3.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.paging3.data.local.TodoDatabase
import com.example.paging3.data.local.TodoEntity
import com.example.paging3.data.remote.TodoApi
import com.example.paging3.data.remote.TodoRemoteMediator
import com.example.paging3.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoApi(): TodoApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideBeerDatabase(
        @ApplicationContext context: Context
    ): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "beers.db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideBeerPager(todosDb: TodoDatabase, todosApi: TodoApi): Pager<Int, TodoEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = TodoRemoteMediator(
                todosApi,
                todosDb
            ),
            pagingSourceFactory = {
                todosDb.dao.pageSource()
            }
        )
    }
}