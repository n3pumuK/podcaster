package de.exercicse.jrossbach.podcaster.core.network

import okhttp3.Interceptor
import okhttp3.Response



class AuthorizationIntercepter: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.header("X-ListenAPI-Key", "a822a742df564d5f876713fce68e0c9f")
        return chain.proceed(request.build())
    }
}