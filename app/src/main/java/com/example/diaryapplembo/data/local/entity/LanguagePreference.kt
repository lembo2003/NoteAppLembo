package com.example.diaryapplembo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language_preferences")
data class LanguagePreference(
    @PrimaryKey
    val id: Int = 1,  // Single row entity
    val languageCode: String,
    val lastUpdated: Long
) 