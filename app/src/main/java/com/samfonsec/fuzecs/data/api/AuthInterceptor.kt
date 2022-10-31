package com.samfonsec.fuzecs.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(AUTH_KEY, TOKEN)
            .build()

        return chain.proceed(request)
    }

    companion object {
        private const val AUTH_KEY = "Authorization"
        private const val TOKEN = "Bearer g7vUh-3W9uDI9nyHpxUN9pbUuSJWHgncZCy62xCZHDALl4JVXSk"
    }
}