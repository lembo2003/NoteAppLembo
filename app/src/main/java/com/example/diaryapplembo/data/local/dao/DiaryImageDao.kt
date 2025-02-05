package com.example.diaryapplembo.data.local.dao

import androidx.room.*
import com.example.diaryapplembo.data.local.entity.DiaryImage
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryImageDao {
    @Query("SELECT * FROM diary_images WHERE diaryId = :diaryId")
    fun getImagesForEntry(diaryId: Long): Flow<List<DiaryImage>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: DiaryImage): Long
    
    @Delete
    suspend fun deleteImage(image: DiaryImage)
} 