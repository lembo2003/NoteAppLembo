package com.example.diaryapplembo.data.repository

import android.content.Context
import com.example.diaryapplembo.data.local.dao.DiaryImageDao
import com.example.diaryapplembo.data.local.entity.DiaryImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val context: Context,
    private val imageDao: DiaryImageDao
) : ImageRepository {
    
    override fun getImagesForEntry(diaryId: Long): Flow<List<DiaryImage>> {
        return imageDao.getImagesForEntry(diaryId)
    }
    
    override suspend fun insertImage(image: DiaryImage): Long {
        return imageDao.insertImage(image)
    }
    
    override suspend fun deleteImage(image: DiaryImage) {
        imageDao.deleteImage(image)
        deleteImageFromStorage(image.imagePath)
    }
    
    override suspend fun saveImageToStorage(imageBytes: ByteArray): String = withContext(Dispatchers.IO) {
        val fileName = "diary_image_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        file.writeBytes(imageBytes)
        file.absolutePath
    }
    
    override suspend fun deleteImageFromStorage(imagePath: String) {
        withContext(Dispatchers.IO) {
            File(imagePath).delete()
        }
    }
} 