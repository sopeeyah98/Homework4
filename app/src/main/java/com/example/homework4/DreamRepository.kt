package com.example.homework4

import kotlinx.coroutines.flow.Flow

class DreamRepository (private val dreamDAO: DreamDAO){
    val allDreams: Flow<List<Dream>> = dreamDAO.getDreams()

    suspend fun insertDream(dream: Dream){
        dreamDAO.insertDream(dream)
    }

    suspend fun updateDream(title:String, content:String, reflection:String, emotion:String, id:Int){
        dreamDAO.updateDream(title, content, reflection, emotion, id)
    }

    suspend fun delete(id:Int){
        dreamDAO.delete(id)
    }

    fun getDream(id:Int): Flow<Dream>{
        return dreamDAO.getDream(id)
    }
}