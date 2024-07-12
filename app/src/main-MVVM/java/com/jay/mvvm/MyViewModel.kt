package com.jay.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Model--View--ViewModel
 */

class MyViewModel : ViewModel() {
    val liveData: MutableLiveData<String> = MutableLiveData()
    override fun onCleared() {
        super.onCleared()
    }

    // TODO: save to locale repository or database
    // TODO: fetch data from network repository
}