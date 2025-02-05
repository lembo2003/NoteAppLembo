package com.example.diaryapplembo.data.repository

import com.example.diaryapplembo.data.local.dao.DiaryDao
import com.example.diaryapplembo.data.local.entity.DiaryEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao
) : DiaryRepository {
    
    override fun getAllEntries(): Flow<List<DiaryEntry>> {
        return diaryDao.getAllEntries()
    }
    
    override fun searchEntries(query: String): Flow<List<DiaryEntry>> {
        return diaryDao.searchEntries(query)
    }
    
    override suspend fun getEntryById(id: Long): DiaryEntry? {
        return diaryDao.getEntryById(id)
    }
    
    override suspend fun insertEntry(entry: DiaryEntry): Long {
        return diaryDao.insertEntry(entry)
    }
    
    override suspend fun updateEntry(entry: DiaryEntry) {
        diaryDao.updateEntry(entry)
    }
    
    override suspend fun deleteEntry(entry: DiaryEntry) {
        diaryDao.deleteEntry(entry)
    }
} 