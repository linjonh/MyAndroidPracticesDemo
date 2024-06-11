package com.jay.mypracticesdemo

import com.jay.mypraticesdemon.MyTestMark
import com.jay.mypraticesdemon.OKHttpTest
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun TestOkHttp() {
        val clazz = Class.forName(OKHttpTest::class.java.name)
        val newInstance = clazz.getDeclaredConstructor().newInstance()
        clazz.declaredMethods.forEach { method ->
            println("=========>declaredMethod=[${method.name}] method.annotations=${method.declaredAnnotations.size}")
            method.annotations.forEach {
                val simpleName = it.annotationClass.simpleName
                println("annotation=$simpleName")
                if (simpleName == MyTestMark::class.java.simpleName) {
                    println("method.invoke=${method.name}")
                    try {

                        method.invoke(newInstance)
                    } catch (e: Throwable) {
                        println("${method.name} Exception " + e.cause)
                    }
                }
            }
        }
    }
}