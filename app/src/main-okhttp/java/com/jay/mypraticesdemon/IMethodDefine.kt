package com.jay.mypraticesdemon

import okhttp3.Request

interface IMethodDefine {
    /**
     * get方法
     */
    fun getRequest(url: String = TestUrl): Request

    /**
     * post方法
     */
    fun postRequest(url: String = PostUrl, postString: String? = PostUrl): Request
    /**
     * 同步请求方式 {execute()},等待请求成功后使用返回值
     */
    fun synchronousCall(request: Request? = null)
    /**
     * 异步请求方式调用 {enqueue()}，异步等待数据返回
     */
    fun asynchronousCall(request: Request? = null)
    /**
     * 传输一段markDown格式的字符串
     */
    fun postACharString()
    /**
     * 传输一个数据流
     */
    fun postStreaming()
    /**
     * 上传文件
     */
    fun postAFile()
    /**
     * 提交单个表单数据
     */
    fun postFormParametersData()
    /**
     * 上传多个FormDataPart的情况，如文字，和图片表格同时上传
     */
    fun postMultipartRequest()

    fun handleAuthentication()
}