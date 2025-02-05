package com.example.diaryapplembo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.diaryapplembo.data.local.dao.DiaryDao
import com.example.diaryapplembo.data.local.dao.DiaryImageDao
import com.example.diaryapplembo.data.local.entity.DiaryEntry
import com.example.diaryapplembo.data.local.entity.DiaryImage
import com.example.diaryapplembo.data.local.entity.LanguagePreference

@Database(
    entities = [DiaryEntry::class, DiaryImage::class, LanguagePreference::class],
    version = 1
)
abstract class DiaryDatabase : RoomDatabase() {
    abstract val diaryDao: DiaryDao
    abstract val diaryImageDao: DiaryImageDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryDatabase? = null

        fun getInstance(context: Context): DiaryDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java,
                    "diary_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
} 