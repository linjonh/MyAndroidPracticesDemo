package com.jay.mypraticesdemon

import retrofit2.Call
import retrofit2.Retrofit
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
class RetrofitRestAPITest {
    @MyTestMark
    fun createRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .build()
        //Each Call from the created GitHubService can make a synchronous or asynchronous HTTP request to the remote webserver.
        val gitHubService = retrofit.create(GitHubService::class.java)
        gitHubService.listRepos("octocat")
    }


    interface GitHubService {
        @GET("users/{user}/repos")
        fun listRepos(@Path("user") user: String?): Call<List<Any?>?>?
    }

}