package com.jay.mypracticesdemo

import com.jay.mypraticesdemon.MyTestableMark
import com.jay.mypraticesdemon.OKHttpDemo
import com.jay.mypraticesdemon.RetrofitRestAPIDemo
import org.junit.Test
import java.io.File
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class OkHttpUnitTest {
    private fun testClass(className: String) {
        val clazz = Class.forName(className)
        val newInstance = clazz.getDeclaredConstructor().newInstance()
        clazz.declaredMethods.forEach { method ->
            println("=========>declaredMethod=[${method.name}] method.annotations=${method.declaredAnnotations.size}")
            method.annotations.forEach {
                val simpleName = it.annotationClass.simpleName
                println("annotation=$simpleName it=$it")
                if (it is MyTestableMark) {
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

    //测试okhttp
    @Test
    fun TestOkHttp() {
        testClass(OKHttpDemo::class.java.name)
    }

    //测试retrofit
    @Test
    fun testRetrofit() {
        testClass(RetrofitRestAPIDemo::class.java.name)
    }

}