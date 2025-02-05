package com.example.diaryapplembo.data.repository

import com.example.diaryapplembo.data.local.entity.DiaryEntry
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    fun getAllEntries(): Flow<List<DiaryEntry>>
    fun searchEntries(query: String): Flow<List<DiaryEntry>>
    suspend fun getEntryById(id: Long): DiaryEntry?
    suspend fun insertEntry(entry: DiaryEntry): Long
    suspend fun updateEntry(entry: DiaryEntry)
    suspend fun deleteEntry(entry: DiaryEntry)
} 