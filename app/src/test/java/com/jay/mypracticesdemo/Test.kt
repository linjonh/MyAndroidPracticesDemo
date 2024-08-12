package com.jay.mypracticesdemo

import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class Test {
    @Test
    fun test() {
        runBlocking {
            var isDone = true
            while (isDone) {
                println("Test")
                // do something
                KtConcurrentDemo().main()
                println("end test")
                isDone = false
            }
        }

    }
}