package com.example.diaryapplembo.data.repository

import com.example.diaryapplembo.data.local.entity.LanguagePreference
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getLanguagePreference(): Flow<LanguagePreference>
    suspend fun updateLanguagePreference(languageCode: String)
} 