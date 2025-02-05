package com.example.diaryapplembo.presentation.event

import com.example.diaryapplembo.data.local.entity.DiaryEntry
import com.example.diaryapplembo.data.local.entity.DiaryImage

sealed class DiaryEvent {
    data class SaveEntry(
        val title: String,
        val content: String,
        val emotion: String
    ) : DiaryEvent()
    
    data class UpdateEntry(
        val id: Long,
        val title: String,
        val content: String,
        val emotion: String
    ) : DiaryEvent()
    
    data class DeleteEntry(val entry: DiaryEntry) : DiaryEvent()
    data class SelectEntry(val id: Long) : DiaryEvent()
    data class SearchEntries(val query: String) : DiaryEvent()
    data class AddImage(val imageBytes: ByteArray, val caption: String?) : DiaryEvent()
    data class DeleteImage(val image: DiaryImage) : DiaryEvent()
    data class SetLanguage(val languageCode: String) : DiaryEvent()
    object ClearCurrentEntry : DiaryEvent()
} 