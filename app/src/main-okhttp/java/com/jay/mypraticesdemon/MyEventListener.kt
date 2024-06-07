package com.jay.mypraticesdemon

import okhttp3.Call
import okhttp3.Connection
import okhttp3.EventListener
import okhttp3.Handshake
import okhttp3.HttpUrl
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy

class MyEventListener : EventListener() {
    override fun cacheConditionalHit(call: Call, cachedResponse: Response) {
        super.cacheConditionalHit(call, cachedResponse)
    }

    override fun cacheHit(call: Call, response: Response) {
        super.cacheHit(call, response)
    }

    override fun cacheMiss(call: Call) {
        super.cacheMiss(call)
    }

    override fun callEnd(call: Call) {
        super.callEnd(call)
        println("callEnd=$call")
    }

    override fun callFailed(call: Call, ioe: IOException) {
        super.callFailed(call, ioe)
        println("callFailed=$call ioe=$ioe")

    }

    override fun callStart(call: Call) {
        super.callStart(call)
        println("callStart=$call")
    }

    override fun canceled(call: Call) {
        super.canceled(call)
        println("canceled=$call")

    }

    override fun connectEnd(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol)
        println("connectEnd=$call inetSocketAddress=$inetSocketAddress proxy=$proxy protocol=$protocol")
    }

    override fun connectFailed(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?, ioe: IOException) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe)
        println("connectFailed=$call inetSocketAddress=$inetSocketAddress proxy=$proxy protocol=$protocol ioe=$ioe")
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        super.connectStart(call, inetSocketAddress, proxy)
        println("connectStart=$call inetSocketAddress=$inetSocketAddress proxy=$proxy")
    }

    override fun connectionAcquired(call: Call, connection: Connection) {
        super.connectionAcquired(call, connection)
        println("connectionAcquired=$call connection=$connection")

    }

    override fun connectionReleased(call: Call, connection: Connection) {
        super.connectionReleased(call, connection)
        println("connectionReleased=$call connection=$connection")

    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        super.dnsEnd(call, domainName, inetAddressList)
        println(
            "dnsEnd=$call domainName=$domainName inetAddressList=${
                inetAddressList.reduce({ acc, inetAddress ->
                    acc.hostAddress + " " + inetAddress.hostAddress
                    acc
                })
            }"
        )

    }

    override fun dnsStart(call: Call, domainName: String) {
        super.dnsStart(call, domainName)
    }

    override fun proxySelectEnd(call: Call, url: HttpUrl, proxies: List<Proxy>) {
        super.proxySelectEnd(call, url, proxies)
    }

    override fun proxySelectStart(call: Call, url: HttpUrl) {
        super.proxySelectStart(call, url)
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        super.requestBodyEnd(call, byteCount)
    }

    override fun requestBodyStart(call: Call) {
        super.requestBodyStart(call)
    }

    override fun requestFailed(call: Call, ioe: IOException) {
        super.requestFailed(call, ioe)
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        super.requestHeadersEnd(call, request)
    }

    override fun requestHeadersStart(call: Call) {
        super.requestHeadersStart(call)
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        super.responseBodyEnd(call, byteCount)
    }

    override fun responseBodyStart(call: Call) {
        super.responseBodyStart(call)
    }

    override fun responseFailed(call: Call, ioe: IOException) {
        super.responseFailed(call, ioe)
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        super.responseHeadersEnd(call, response)
    }

    override fun responseHeadersStart(call: Call) {
        super.responseHeadersStart(call)
    }

    override fun satisfactionFailure(call: Call, response: Response) {
        super.satisfactionFailure(call, response)
    }

    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        super.secureConnectEnd(call, handshake)
    }

    override fun secureConnectStart(call: Call) {
        super.secureConnectStart(call)
    }
}