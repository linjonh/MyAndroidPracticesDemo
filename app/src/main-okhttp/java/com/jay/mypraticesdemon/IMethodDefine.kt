package com.jay.mypraticesdemon

import okhttp3.Request

interface IMethodDefine {
    fun getRequest(url: String = TestUrl): Request
    fun postRequest(url: String = PostUrl, postString: String? = PostUrl): Request

    fun synchronousCall(request: Request? = null)
    fun asynchronousCall(request: Request? = null)

    fun postACharString()
    fun postStreaming()
    fun postAFile()
    fun postFormParametersData()
    fun postMultipartRequest()

    fun handleAuthentication()
}