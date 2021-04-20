package com.example.homework4

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DreamViewModel(private val repository: DreamRepository): ViewModel() {
    val allDreams: LiveData<List<Dream>> = repository.allDreams.asLiveData()

    fun insertDream(dream: Dream) = viewModelScope.launch {
        repository.insertDream(dream)
    }

    fun updateDream(title:String, content:String, reflection:String, emotion:String, id:Int) = viewModelScope.launch {
        repository.updateDream(title, content, reflection, emotion, id)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun getDream(id: Int): LiveData<Dream> = repository.getDream(id).asLiveData()
}

class DreamViewModelFactory(private val repository: DreamRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DreamViewModel::class.java)){
            return DreamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}