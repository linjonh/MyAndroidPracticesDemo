package com.jay.mypraticesdemon

import com.google.gson.internal.LinkedTreeMap
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Retrofit
 * A type-safe HTTP client for Android and Java
 * Use annotations to describe the HTTP request:
 * URL parameter replacement and query parameter support
 * Object conversion to request body (e.g., JSON, protocol buffers)
 * Multipart request body and file upload
 */
class RetrofitRestAPIDemo {
    @MyTestableMark
    fun createRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())//需要使用到的类型转换器，默认的是responseBody
            .build()

        //Each Call from the created GitHubService can make a synchronous or asynchronous HTTP request to the remote webserver.
        val gitHubService = retrofit.create(GitHubService::class.java)
//        val listRepos = gitHubService.listRepos("octocat")
        val listRepos = gitHubService.listRepos("linjonh")

        listRepos.let {
            val response = it?.execute()
            response?.let {
                it.body()?.forEachIndexed { index, item ->
                    val value = item as LinkedTreeMap<*, *>
                    println("item $index=${value["name"]}")
                }
            }
        }
    }


    interface GitHubService {
        @GET("users/{user}/repos")
        fun listRepos(@Path("user") user: String?): Call<List<Any?>?>?
    }

}