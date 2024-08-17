package com.jay.mypracticesdemo

import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class Test {
    @Test
    fun test() {
//        runBlocking {
//            var isDone = true
//            while (isDone) {
//                println("Test")
//                // do something
//                KtConcurrentDemo().main()
//                println("end test")
//                isDone = false
//            }
//        }
        inlinedFunction("text", {
            val a = 3
            val b = 2
            println("block1 sum=a+b=${a + b}")
        }, {
            val a = 1
            val b = 2
            println("block2 sum=a+b=${a + b}")
        }, {
            val a = 2
            val b = 2
            println("block3 sum=a+b=${a + b}")
        })

        doAction {
            println("doAction")
            doAction {
                println("doAction1")
                return
            }
            return
        }
    }

    inline fun inlinedFunction(
        text: String,
        noinline block: () -> Unit,
        crossinline block1: () -> Unit,
        block2: () -> Unit
    ) {
        println("inlineFunction text=$text")
        block()
        block1()
        block2()
        println("end inlineFunction")
    }

    inline fun doAction(callback: () -> Unit) {
        callback()
    }
}