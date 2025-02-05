package com.example.diaryapplembo.presentation.state

import com.example.diaryapplembo.data.local.entity.DiaryEntry
import com.example.diaryapplembo.data.local.entity.DiaryImage

data class DiaryState(
    val entries: List<DiaryEntry> = emptyList(),
    val currentEntry: DiaryEntry? = null,
    val currentImages: List<DiaryImage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val selectedLanguage: String = "en"
) 