package com.example.exercises

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlinx.coroutines.Dispatchers

class MainViewModel: ViewModel() {
    private val repository: WikiRepository = WikiRepository()
    private val query = MutableLiveData<String>()

    fun queryName(name: String) {query.value = name }

    val hitCount = query.switchMap {
        liveData(Dispatchers.IO) {emit(repository.hitCountCheck(it))}
    }
}