package com.example.diaryapplembo.data.local.dao

import androidx.room.*
import com.example.diaryapplembo.data.local.entity.DiaryEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary_entries ORDER BY date DESC")
    fun getAllEntries(): Flow<List<DiaryEntry>>
    
    @Query("SELECT * FROM diary_entries WHERE id = :id")
    suspend fun getEntryById(id: Long): DiaryEntry?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: DiaryEntry): Long
    
    @Update
    suspend fun updateEntry(entry: DiaryEntry)
    
    @Delete
    suspend fun deleteEntry(entry: DiaryEntry)
    
    @Query("SELECT * FROM diary_entries WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchEntries(query: String): Flow<List<DiaryEntry>>
} 