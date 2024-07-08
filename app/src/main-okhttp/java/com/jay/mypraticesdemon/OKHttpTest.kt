package com.jay.mypraticesdemon


import okhttp3.Authenticator
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import okio.BufferedSink
import okio.IOException
import java.io.File
import java.util.concurrent.TimeUnit

const val TestUrl = "http://www.baid.com"
const val PostUrl = "https://api.github.com/markdown/raw"
val MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8".toMediaType()

class OKHttpTest : IMethodDefine {
    fun getOkhttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient()
        //http协议log拦截器
        val interceptor = HttpLoggingInterceptor()

        okHttpClient.newBuilder()//现有的Client变量上新创建一个Client builder
            .addInterceptor(interceptor)
            .addNetworkInterceptor(interceptor)
            .callTimeout(10, TimeUnit.SECONDS)
            .eventListener(MyEventListener())
            .build()
        return okHttpClient
    }

    fun printHeaders(headers: Headers) {
        println()
        for ((name, value) in headers) {
            println("$name: $value")
        }
    }

    fun printSuccessContent(it: Response) {
        if (!it.isSuccessful) println("request failed: $it")
        else {
            val headers = it.headers("Accept")
            println("To visit all headers, use the Headers class which supports access by index. headers=$headers")
            printHeaders(it.headers)
            println(it.body.string())
        }
    }

    /**
     * get 请求方法
     */
    override fun getRequest(url: String): Request {
        val request = Request
            .Builder()
            .header("User-Agent", "Kotlin API Test client")
            .url(url)
            .build()
        return request
    }

    override fun postRequest(url: String, postString: String?): Request {
        var postBody = """
        |Releases
        |--------
        |
        | * _1.0_ May 6, 2013
        | * _1.1_ June 15, 2013
        | * _1.2_ August 11, 2013
        |""".trimMargin()
        if (!postString.isNullOrEmpty()) {
            postBody = postString
        }
        val request = Request
            .Builder()
            .header("User-Agent", "Kotlin API Test client")
            .url(url)
            .post(postBody.toRequestBody(MEDIA_TYPE_MARKDOWN))
            .build()
        return request
    }

    /**
     * 同步请求方式 {execute()},等待请求成功后使用返回值
     */
    override fun synchronousCall(request: Request?) {
        getOkhttpClient()
            .newCall(request ?: getRequest(TestUrl))
            .execute().use {
                printSuccessContent(it)
            }
    }


    /**
     * 异步请求方式调用 {enqueue()}，异步等待数据返回
     */
    override fun asynchronousCall(request: Request?) {
        getOkhttpClient()
            .newCall(request ?: getRequest())
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e.message)
                }

                override fun onResponse(call: Call, response: Response) {
                    printSuccessContent(response)
                }
            })
    }

    /**
     * 传输一段markDown格式的字符串
     */
    @MyTestMark
    override fun postACharString() {
        val request = postRequest(PostUrl)
        synchronousCall(request)
    }

    /**
     * 传输一个数据流
     */
    @MyTestMark
    override fun postStreaming() {
        val requestBody: RequestBody = object : RequestBody() {
            override fun contentType() = MEDIA_TYPE_MARKDOWN

            override fun writeTo(sink: BufferedSink) {
                sink.writeUtf8("Numbers\n")
                sink.writeUtf8("-------\n")
                for (i in 2..997) {
                    sink.writeUtf8(String.format(" * $i = ${factor(i)}\n"))
                }
            }

            private fun factor(n: Int): String {
                for (i in 2 until n) {
                    val x = n / i
                    if (x * i == n) return "${factor(x)} × $i"
                }
                return n.toString()
            }
        }
        val request = postRequest(PostUrl)
            .newBuilder()
            .post(requestBody)// stream body
            .build()
        synchronousCall(request)
    }

    /**
     * 上传文件
     */
    @MyTestMark
    override fun postAFile() {
        val file = File("README.md")
        file.writeText("Test post a file Stream")
        val request = postRequest()
            .newBuilder()
            .post(
                file.asRequestBody(MEDIA_TYPE_MARKDOWN)
            )
            .build()
        synchronousCall(request)
    }

    /**
     * 提交单个表单数据
     */
    @MyTestMark
    override fun postFormParametersData() {
        val formBody = FormBody.Builder()
            .add("name", "lin")
            .add("action", "post")
            .add("age", "23")
            .build()

        val request = postRequest()
            .newBuilder()
            .post(formBody)// form body
            .build()
        synchronousCall(request)
    }

    /**
     * 上传多个FormDataPart的情况，如文字，和图片表格同时上传
     */
    @MyTestMark
    override fun postMultipartRequest() {
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("title", "Square Logo")
            .addFormDataPart(
                "image", "th.webp",
                // TODO: file replace
                File("th.webp").asRequestBody(MEDIA_TYPE_PNG)
            )
            .build()

        val request = Request.Builder()
            .header("Authorization", "Client-ID $IMGUR_CLIENT_ID")
            .url("https://api.imgur.com/3/image")
            .post(requestBody)
            .build()
        synchronousCall(request)
    }

    /**
     * 授权认证测试
     */
    @MyTestMark
    override fun handleAuthentication() {
        val request = Request.Builder()
            .url("http://publicobject.com/secrets/hellosecret.txt")
            .build()
        val authenticator = object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                if (response.request.header("Authorization") != null) {
                    return null // Give up, we've already attempted to authenticate.
                }
                println("Authenticating for response: $response")
                println("Challenges: ${response.challenges()}")
                val credential = Credentials.basic("jesse", "password1")
                return response.request.newBuilder()
                    .header("Authorization", credential)
                    .build()
            }
        }
        getOkhttpClient()
            .newBuilder()
            .authenticator(authenticator)
            .build().newCall(request).execute().use { printSuccessContent(it) }

    }

    /**
     * The imgur client ID for OkHttp recipes. If you're using imgur for anything other than running
     * these examples, please request your own client ID! https://api.imgur.com/oauth2
     */
    private val IMGUR_CLIENT_ID = "9199fdef135c122"
    private val MEDIA_TYPE_PNG = "image/webp".toMediaType()
}

