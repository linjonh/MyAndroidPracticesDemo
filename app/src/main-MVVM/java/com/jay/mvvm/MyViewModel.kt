package com.jay.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Model--View--ViewModel
 */

class MyViewModel : ViewModel() {
    val liveData: MutableLiveData<String> = MutableLiveData()
    override fun onCleared() {
        super.onCleared()
    }

    // TODO: save to locale repository or database
    fun saveData(){
        viewModelScope.launch {

        }
    }
    // TODO: fetch data from network repository
    fun loadData(){
        viewModelScope.launch {

        }
    }
}