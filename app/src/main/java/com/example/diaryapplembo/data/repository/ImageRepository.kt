package com.example.diaryapplembo.data.repository

import com.example.diaryapplembo.data.local.entity.DiaryImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImagesForEntry(diaryId: Long): Flow<List<DiaryImage>>
    suspend fun insertImage(image: DiaryImage): Long
    suspend fun deleteImage(image: DiaryImage)
    suspend fun saveImageToStorage(imageBytes: ByteArray): String
    suspend fun deleteImageFromStorage(imagePath: String)
} 