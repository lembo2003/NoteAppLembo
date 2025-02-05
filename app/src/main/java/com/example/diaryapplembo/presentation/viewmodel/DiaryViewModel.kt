package com.example.diaryapplembo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diaryapplembo.data.local.entity.DiaryEntry
import com.example.diaryapplembo.data.local.entity.DiaryImage
import com.example.diaryapplembo.data.repository.DiaryRepository
import com.example.diaryapplembo.data.repository.ImageRepository
import com.example.diaryapplembo.data.repository.LanguageRepository
import com.example.diaryapplembo.presentation.event.DiaryEvent
import com.example.diaryapplembo.presentation.state.DiaryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val imageRepository: ImageRepository,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DiaryState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            // Combine diary entries with search query
            combine(
                diaryRepository.getAllEntries(),
                _state.map { it.searchQuery }
            ) { entries, query ->
                if (query.isBlank()) {
                    entries
                } else {
                    entries.filter { 
                        it.title.contains(query, ignoreCase = true) ||
                        it.content.contains(query, ignoreCase = true)
                    }
                }
            }.collect { entries ->
                _state.update { it.copy(entries = entries) }
            }
        }
    }

    fun onEvent(event: DiaryEvent) {
        when (event) {
            is DiaryEvent.SaveEntry -> {
                viewModelScope.launch {
                    try {
                        val entry = DiaryEntry(
                            title = event.title,
                            content = event.content,
                            emotion = event.emotion,
                            date = System.currentTimeMillis(),
                            lastModified = System.currentTimeMillis(),
                            language = state.value.selectedLanguage
                        )
                        diaryRepository.insertEntry(entry)
                    } catch (e: Exception) {
                        _state.update { it.copy(error = e.message) }
                    }
                }
            }

            is DiaryEvent.UpdateEntry -> {
                viewModelScope.launch {
                    try {
                        state.value.currentEntry?.let { currentEntry ->
                            val updatedEntry = currentEntry.copy(
                                title = event.title,
                                content = event.content,
                                emotion = event.emotion,
                                lastModified = System.currentTimeMillis()
                            )
                            diaryRepository.updateEntry(updatedEntry)
                        }
                    } catch (e: Exception) {
                        _state.update { it.copy(error = e.message) }
                    }
                }
            }

            is DiaryEvent.DeleteEntry -> {
                viewModelScope.launch {
                    try {
                        diaryRepository.deleteEntry(event.entry)
                    } catch (e: Exception) {
                        _state.update { it.copy(error = e.message) }
                    }
                }
            }

            is DiaryEvent.SelectEntry -> {
                viewModelScope.launch {
                    try {
                        val entry = diaryRepository.getEntryById(event.id)
                        val images = imageRepository.getImagesForEntry(event.id)
                            .first() // Get current list of images
                        
                        _state.update { 
                            it.copy(
                                currentEntry = entry,
                                currentImages = images
                            )
                        }
                    } catch (e: Exception) {
                        _state.update { it.copy(error = e.message) }
                    }
                }
            }

            is DiaryEvent.SearchEntries -> {
                _state.update { it.copy(searchQuery = event.query) }
            }

            is DiaryEvent.AddImage -> {
                viewModelScope.launch {
                    try {
                        state.value.currentEntry?.let { entry ->
                            val imagePath = imageRepository.saveImageToStorage(event.imageBytes)
                            val image = DiaryImage(
                                diaryId = entry.id,
                                imagePath = imagePath,
                                caption = event.caption,
                                timestamp = System.currentTimeMillis()
                            )
                            imageRepository.insertImage(image)
                        }
                    } catch (e: Exception) {
                        _state.update { it.copy(error = e.message) }
                    }
                }
            }

            is DiaryEvent.DeleteImage -> {
                viewModelScope.launch {
                    try {
                        imageRepository.deleteImage(event.image)
                    } catch (e: Exception) {
                        _state.update { it.copy(error = e.message) }
                    }
                }
            }

            is DiaryEvent.SetLanguage -> {
                viewModelScope.launch {
                    try {
                        languageRepository.updateLanguagePreference(event.languageCode)
                        _state.update { it.copy(selectedLanguage = event.languageCode) }
                    } catch (e: Exception) {
                        _state.update { it.copy(error = e.message) }
                    }
                }
            }

            DiaryEvent.ClearCurrentEntry -> {
                _state.update { 
                    it.copy(
                        currentEntry = null,
                        currentImages = emptyList()
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
} 