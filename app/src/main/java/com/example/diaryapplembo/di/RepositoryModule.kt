package com.example.diaryapplembo.di

import android.content.Context
import com.example.diaryapplembo.data.local.dao.DiaryDao
import com.example.diaryapplembo.data.local.dao.DiaryImageDao
import com.example.diaryapplembo.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideDiaryRepository(diaryDao: DiaryDao): DiaryRepository {
        return DiaryRepositoryImpl(diaryDao)
    }
    
    @Provides
    @Singleton
    fun provideImageRepository(
        @ApplicationContext context: Context,
        imageDao: DiaryImageDao
    ): ImageRepository {
        return ImageRepositoryImpl(context, imageDao)
    }
} 