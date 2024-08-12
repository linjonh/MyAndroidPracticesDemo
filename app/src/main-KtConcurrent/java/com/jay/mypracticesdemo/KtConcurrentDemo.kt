package com.jay.mypracticesdemo

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class KtConcurrentDemo {
    suspend fun main(): String {
        val startTime = System.currentTimeMillis()
        val myIOConcurrentScope = MyIOConcurrentScope()
        var loadFakeData: String = "null"
        println("start job")
        val job = myIOConcurrentScope.launch {
            // do something
            loadFakeData = loadFakeData()
            println(loadFakeData)
        }
        job.join()
        println("end job time=${System.currentTimeMillis() - startTime}")
        return loadFakeData
    }

    private suspend fun loadFakeData(): String {
        println("start loadFakeData and delay 5s")
        delay(5000)
        return "do something"
    }
}