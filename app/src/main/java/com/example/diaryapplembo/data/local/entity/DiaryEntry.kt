package com.example.diaryapplembo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_entries")
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val date: Long,  // Timestamp
    val emotion: String,
    val lastModified: Long,
    val isEncrypted: Boolean = false,
    val language: String
) 